package project.guakamole.domain.copyright.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Repository;
import project.guakamole.domain.copyright.dto.response.FindCopyrightResponse;
import org.springframework.data.domain.Pageable;
import project.guakamole.domain.copyright.searchtype.CopyrightSearchType;

import java.util.List;

import static project.guakamole.domain.copyright.entity.QCopyright.*;

@Repository
@RequiredArgsConstructor
public class CopyrightSearchRepositoryImpl implements CopyrightSearchRepository{

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<FindCopyrightResponse> searchCopyright(
            CopyrightSearchType searchType,
            String keyword,
            Pageable pageable)
    {
        List<FindCopyrightResponse> response = queryFactory
                .select(
                        Projections.constructor(FindCopyrightResponse.class,
                                copyright.id,
                                copyright.copyrightName,
                                copyright.ownerName,
                                copyright.originalLink,
                                copyright.createdDate)
                )
                .from(copyright)
                .where(EqSearchCond(searchType, keyword))
                .orderBy(copyright.createdDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long count = queryFactory
                .select(copyright.id.count())
                .from(copyright)
                .where(EqSearchCond(searchType, keyword))
                .fetchOne()
        ;

        return new PageImpl<>(response, pageable, count);
    }

    private static BooleanExpression EqSearchCond(CopyrightSearchType searchType, String keyword) {
        if(keyword == null)
            return null;

        if(searchType == null && isLong(keyword))
            return copyright.id.eq(Long.valueOf(keyword)).
                    or(copyright.ownerName.contains(keyword)).
                    or(copyright.copyrightName.contains(keyword));

        if(searchType == null && !isLong(keyword))
            return copyright.ownerName.contains(keyword).
                    or(copyright.copyrightName.contains(keyword));

        if (searchType == CopyrightSearchType.SOURCE_ID && isLong(keyword))
            return copyright.id.eq(Long.valueOf(keyword));

        if (searchType == CopyrightSearchType.OWNER_NAME)
            return copyright.ownerName.contains(keyword);

        if (searchType == CopyrightSearchType.COPYRIGHT_NAME)
            return copyright.copyrightName.contains(keyword);

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

