package project.guakamole.domain.applicant.repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import project.guakamole.domain.applicant.dto.response.FindApplicantResponse;
import project.guakamole.domain.applicant.entity.ApplicantApproveStatus;
import project.guakamole.domain.applicant.searchtype.ApplicantSearchType;

import java.util.List;

import static project.guakamole.domain.applicant.entity.QApplicant.applicant;

@Repository
@RequiredArgsConstructor
public class ApplicantSearchRepositoryImpl implements ApplicantSearchRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public Page<FindApplicantResponse> searchApplicant(ApplicantSearchType searchType, String keyword, Pageable pageable) {
        List<FindApplicantResponse> responses = queryFactory.
                select(
                        Projections.constructor(FindApplicantResponse.class,
                                applicant.id,
                                applicant.chanelName,
                                applicant.chanelLink
                        )
                )
                .from(applicant)
                .where(EqSearchCond(searchType, keyword))
                .orderBy(orderCond, applicant.createdDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch()
                ;

        Long count = queryFactory
                .select(applicant.id.count())
                .from(applicant)
                .where(EqSearchCond(searchType, keyword))
                .fetchOne()
                ;

        return new PageImpl<>(responses, pageable, count);
    }

    private static BooleanExpression EqSearchCond(ApplicantSearchType searchType, String keyword) {
        if(keyword == null)
            return null;

        if(searchType == null && isLong(keyword))
            return applicant.id.eq(Long.valueOf(keyword)).
                    or(applicant.chanelName.contains(keyword)).
                    or(applicant.email.contains(keyword));

        if(searchType == null && !isLong(keyword))
            return applicant.chanelName.contains(keyword)
                    .or(applicant.email.contains(keyword));

        if (searchType == ApplicantSearchType.USER_ID && isLong(keyword))
            return applicant.id.eq(Long.valueOf(keyword));

        if (searchType == ApplicantSearchType.CHANEL_NAME)
            return applicant.chanelName.contains(keyword);

        if (searchType == ApplicantSearchType.EMAIL)
            return applicant.email.contains(keyword);

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

    private OrderSpecifier<Integer> orderCond = new CaseBuilder()
            .when(applicant.approveStatus.eq(ApplicantApproveStatus.HOLD)).then(1)
            .when(applicant.approveStatus.eq(ApplicantApproveStatus.APPROVE)).then(2)
            .when(applicant.approveStatus.eq(ApplicantApproveStatus.DECLINE)).then(3)
            .otherwise(4)
            .asc(); // 오름차순으로 정렬
}
