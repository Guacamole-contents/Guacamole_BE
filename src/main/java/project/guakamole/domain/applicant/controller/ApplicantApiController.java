package project.guakamole.domain.applicant.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.guakamole.domain.applicant.dto.request.ReviewApplicantRequest;
import project.guakamole.domain.applicant.dto.response.DetailApplicantResponse;
import project.guakamole.domain.applicant.service.ApplicantService;
import project.guakamole.domain.common.dto.PageResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/applicants")
public class ApplicantApiController {

    private final ApplicantService applicantService;

    @GetMapping
    public ResponseEntity<PageResponse> findApplicants(
            @PageableDefault(sort = "id", direction = Sort.Direction.DESC, size = 10, page = 0) Pageable pageable)
    {
        return ResponseEntity.ok(applicantService.findApplicants(pageable));
    }

    @GetMapping
    public ResponseEntity<PageResponse> searchApplicant(
            @PageableDefault(sort = "id", direction = Sort.Direction.DESC, size = 10, page = 0) Pageable pageable,
            @RequestParam(value = "searchType", required = false) Integer searchType,
            @RequestParam(value = "keyword", required = false) String keyword)
    {
        return ResponseEntity.ok(applicantService.searchApplicant(searchType, keyword, pageable));
    }

    @GetMapping("/{applicantId}")
    public ResponseEntity<DetailApplicantResponse> findApplicant(
            @PathVariable("applicantId") Long applicantId)
    {
        return ResponseEntity.ok(applicantService.findApplicant(applicantId));
    }

    @PostMapping("/{applicantId}")
    public ResponseEntity<Void> reviewApplicant(
            @PathVariable("applicantId") Long applicantId,
            @RequestBody ReviewApplicantRequest request)
    {
        applicantService.reviewApplicant(applicantId, request);

        return ResponseEntity.noContent().build();
    }
}
