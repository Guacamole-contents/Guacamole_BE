package project.guakamole.domain.applicant.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.guakamole.domain.applicant.dto.response.FindApplicantResponse;
import project.guakamole.domain.applicant.searchtype.ApplicantSearchType;

public interface ApplicantSearchRepository {
    Page<FindApplicantResponse> searchApplicant(ApplicantSearchType searchType, String keyword, Pageable pageable);
}
