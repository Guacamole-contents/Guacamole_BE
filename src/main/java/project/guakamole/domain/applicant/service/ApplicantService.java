package project.guakamole.domain.applicant.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.guakamole.domain.applicant.dto.request.ReviewApplicantRequest;
import project.guakamole.domain.applicant.dto.response.DetailApplicantResponse;
import project.guakamole.domain.applicant.dto.response.FindApplicantResponse;
import project.guakamole.domain.applicant.entity.Applicant;
import project.guakamole.domain.applicant.entity.ApplicantApproveStatus;
import project.guakamole.domain.applicant.repository.ApplicantRepository;
import project.guakamole.domain.applicant.searchtype.ApplicantSearchType;
import project.guakamole.domain.common.dto.PageResponse;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ApplicantService {
    private final ApplicantRepository applicantRepository;

    @Transactional(readOnly = true)
    public PageResponse<List<FindApplicantResponse>> findApplicants(Pageable pageable) {
        Page<Applicant> applicantPages = applicantRepository.findAll(pageable);

        List<FindApplicantResponse> responses = applicantPages.stream()
                .map(FindApplicantResponse::of).collect(Collectors.toList());

        return PageResponse.of(responses, applicantPages);
    }

    @Transactional(readOnly = true)
    public PageResponse<List<FindApplicantResponse>> searchApplicant(
            Integer SearchType,
            String keyword,
            Pageable pageable) {
        Page<FindApplicantResponse> applicantPages = applicantRepository.searchApplicant(ApplicantSearchType.get(SearchType), keyword, pageable);

        return PageResponse.of(applicantPages.getContent(), applicantPages);
    }

    @Transactional
    public void reviewApplicant(Long applicantId, ReviewApplicantRequest request) {
        Applicant applicant = findById(applicantId);

        ApplicantApproveStatus status = ApplicantApproveStatus.get(request.getResult());
        applicant.updateApproveStatus(status);
        applicant.updateNote(request.getNote());

        //승인 되었으니 회원 생성되어야 함.
    }

    public DetailApplicantResponse findApplicant(Long applicantId) {
        Applicant applicant = findById(applicantId);

        return DetailApplicantResponse.of(applicant);
    }

    private Applicant findById(Long applicantId) {
        return applicantRepository.findById(applicantId).orElseThrow(
                () -> new IllegalArgumentException("not found applicant")
        );
    }
}
