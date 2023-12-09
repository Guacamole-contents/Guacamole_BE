package project.guakamole.domain.violation.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.guakamole.domain.violation.dto.FilterViolationDto;
import project.guakamole.domain.violation.dto.response.FindViolationResponse;
import project.guakamole.domain.violation.searchtype.ViolationSearchType;

public interface ViolationFilterRepository {

    Page<FindViolationResponse> searchViolationWithFilter(
            ViolationSearchType searchType,
            String keyword,
            FilterViolationDto filter,
            Pageable pageable);


    Page<FindViolationResponse> findViolationWithFilter(
            FilterViolationDto filter,
            Pageable pageable);
}
