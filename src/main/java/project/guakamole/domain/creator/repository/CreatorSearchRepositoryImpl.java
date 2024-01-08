package project.guakamole.domain.creator.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import project.guakamole.domain.copyright.searchtype.CopyrightSearchType;
import project.guakamole.domain.creator.dto.response.FindCreatorResponse;
import project.guakamole.domain.creator.entity.Creator;
import project.guakamole.domain.creator.entity.QCreator;
import project.guakamole.domain.creator.searchType.CreatorSearchType;

import java.util.List;

import static project.guakamole.domain.copyright.entity.QCopyright.copyright;
import static project.guakamole.domain.creator.entity.QCreator.*;

@Repository
@RequiredArgsConstructor
public class CreatorSearchRepositoryImpl implements CreatorSearchRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<FindCreatorResponse> searchCreator(
            CreatorSearchType searchType,
            String keyword,
            Pageable pageable)
    {

        List<FindCreatorResponse> result = queryFactory
                .select(
                        Projections.constructor(FindCreatorResponse.class,
                                creator.id,
                                creator.name,
                                creator.email,
                                creator.copyrightCount)
                )
                .from(creator)
                .where(EqSearchCond(searchType, keyword))
                .orderBy(creator.createdDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long count = queryFactory
                .select(creator.id.count())
                .from(creator)
                .where(EqSearchCond(searchType, keyword))
                .fetchOne();

        return new PageImpl<>(result, pageable, count);
    }

    private static BooleanExpression EqSearchCond(CreatorSearchType searchType, String keyword) {
        if(keyword == null)
            return null;

        if(searchType == null && isLong(keyword))
            return creator.id.eq(Long.valueOf(keyword)).
                    or(creator.name.contains(keyword)).
                    or(creator.email.contains(keyword));

        if(searchType == null && !isLong(keyword))
            return creator.name.contains(keyword).
                or(creator.email.contains(keyword));

        if (searchType == CreatorSearchType.CREATOR_ID && isLong(keyword))
            return creator.id.eq(Long.valueOf(keyword));

        if (searchType == CreatorSearchType.EMAIL)
            return creator.email.contains(keyword);

        if (searchType == CreatorSearchType.CREATOR_NAME)
            return creator.name.contains(keyword);

        return creator.id.eq(0L);
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
