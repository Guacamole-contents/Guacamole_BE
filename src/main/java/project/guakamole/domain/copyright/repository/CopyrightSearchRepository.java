package project.guakamole.domain.copyright.repository;

import org.springframework.data.domain.Page;
import project.guakamole.domain.copyright.dto.response.FindCopyrightResponse;
import org.springframework.data.domain.Pageable;
import project.guakamole.domain.copyright.searchtype.CopyrightSearchType;


public interface CopyrightSearchRepository {
    Page<FindCopyrightResponse> searchCopyright(CopyrightSearchType searchType, String keyword, Pageable pageable);
}
