package project.guakamole.domain.violation.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import project.guakamole.domain.violation.dto.FilterViolationDto;
import project.guakamole.domain.violation.dto.response.FindViolationResponse;
import project.guakamole.domain.violation.entity.AgreementType;
import project.guakamole.domain.violation.entity.ViolateReactLevel;
import project.guakamole.domain.violation.searchtype.ViolationSearchType;

import java.time.LocalDateTime;
import java.util.List;

import static project.guakamole.domain.copyright.entity.QCopyright.copyright;
import static project.guakamole.domain.violation.entity.QViolation.violation;

@Repository
@RequiredArgsConstructor
public class ViolationSearchRepositoryImpl implements ViolationSearchRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<FindViolationResponse> searchViolationWithFilter(
            ViolationSearchType searchType,
            String keyword,
            FilterViolationDto filter,
            Pageable pageable) {
        List<FindViolationResponse> responses = queryFactory
                .select(
                        Projections.constructor(FindViolationResponse.class,
                                violation.id,
                                violation.createdDate,
                                violation.agreementType.stringValue(),
                                violation.agreementAmount,
                                violation.reactLevel.stringValue(),
                                copyright.id,
                                copyright.ownerName
                        )
                )
                .from(violation)
                .leftJoin(violation.copyright, copyright)
                .where(
                        isEqualToSearchCond(searchType, keyword),
                        containsInDate(filter.getStartDate(), filter.getEndDate()),
                        isEqualToAgreementType(filter.getAgreementTypes()),
                        isEqualToReactLevel(filter.getReactLevels()),
                        containsInAgreementAmount(filter.getMinAgreementAmount(), filter.getMaxAgreementAmount())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long count = queryFactory
                .select(violation.id.count())
                .from(violation)
                .where(
                        isEqualToSearchCond(searchType, keyword),
                        containsInDate(filter.getStartDate(), filter.getEndDate()),
                        isEqualToAgreementType(filter.getAgreementTypes()),
                        isEqualToReactLevel(filter.getReactLevels()),
                        containsInAgreementAmount(filter.getMinAgreementAmount(), filter.getMaxAgreementAmount())
                )
                .fetchOne();

        return new PageImpl<>(responses, pageable, count);
    }

    private BooleanExpression containsInAgreementAmount(Long minAgreementAmount, Long maxAgreementAmount) {
        if (minAgreementAmount == null && maxAgreementAmount == null)
            return null;

        if (maxAgreementAmount == null)
            return violation.agreementAmount.goe(minAgreementAmount);

        if (minAgreementAmount == null)
            return violation.agreementAmount.loe(maxAgreementAmount);

        //minAgreeAmount 보다 크거나 같고 maxAgreementAmount보다 작거나 같다
        return violation.agreementAmount.goe(minAgreementAmount).and(
                violation.agreementAmount.loe(maxAgreementAmount)
        );
    }

    private BooleanExpression isEqualToReactLevel(List<Integer> reactLevels) {
        if (reactLevels == null || reactLevels.isEmpty())
            return null;

        BooleanExpression result = null;

        // 1: 저작권자 검토, 2: 침해 대응중, 3: 대응 완료
        for (Integer code : reactLevels){
            if (result == null) {
                result = violation.reactLevel.eq(ViolateReactLevel.get(code));
            } else {
                result = result.or(violation.reactLevel.eq(ViolateReactLevel.get(code)));
            }
        }

        return result;
    }

    private BooleanExpression isEqualToAgreementType(List<Integer> agreementTypes) {
        if (agreementTypes == null || agreementTypes.isEmpty())
            return null;

        BooleanExpression result = null;

        // 1: 보류, 2: 침해합의, 3: 삭제요청, 4:법적대응
        for (Integer code : agreementTypes){
            if (result == null) {
                result = violation.agreementType.eq(AgreementType.get(code));
            } else {
                result = result.or(violation.agreementType.eq(AgreementType.get(code)));
            }
        }

        return result;
    }

    private BooleanExpression containsInDate(LocalDateTime startDate, LocalDateTime endDate) {
        if (startDate == null && endDate == null)
            return null;

        if (endDate == null)
            return violation.violateDate.goe(startDate);

        if (startDate == null)
            return violation.violateDate.loe(endDate);

        return violation.violateDate.goe(startDate).and(
                violation.violateDate.loe(endDate)
        );
    }

    private static BooleanExpression isEqualToSearchCond(ViolationSearchType searchType, String keyword) {
        if (keyword == null)
            return null;

        if (searchType == null && isLong(keyword))
            return violation.copyright.id.eq(Long.valueOf(keyword)).
                    or(copyright.ownerName.contains(keyword));

        if (searchType == null && !isLong(keyword))
            return violation.copyright.ownerName.contains(keyword);

        if (searchType == ViolationSearchType.SOURCE_ID && isLong(keyword))
            return violation.copyright.id.eq(Long.valueOf(keyword));

        if (searchType == ViolationSearchType.OWNER_NAME)
            return violation.copyright.ownerName.contains(keyword);

        throw new IllegalArgumentException("잘못된 검색 시도입니다.");
    }

    private static boolean isLong(String strValue) {
        try {
            Integer.parseInt(strValue);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }


}
