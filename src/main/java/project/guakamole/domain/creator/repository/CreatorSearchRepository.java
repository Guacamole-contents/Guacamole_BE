package project.guakamole.domain.creator.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.guakamole.domain.creator.dto.response.FindCreatorResponse;
import project.guakamole.domain.creator.searchType.CreatorSearchType;

public interface CreatorSearchRepository {
    Page<FindCreatorResponse> searchCreator(CreatorSearchType searchType, String keyword, Pageable pageable);
}
