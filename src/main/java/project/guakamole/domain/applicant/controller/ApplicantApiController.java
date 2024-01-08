package project.guakamole.domain.applicant.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "서비스 신청자 컨트롤러", description = " 서비스 신청자 관련 컨트롤러 입니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("api/applicants")
public class ApplicantApiController {

    private final ApplicantService applicantService;

    @Operation(summary = "서비스 신청자 목록 조회 api", description = "서비스 신청자 데이터 목록을 조회합니다.")
    @GetMapping
    public ResponseEntity<PageResponse> findApplicants(
            @Parameter(description = "page만 주세요 !") @PageableDefault(sort = "id", direction = Sort.Direction.DESC, size = 10, page = 0) Pageable pageable)
    {
        return ResponseEntity.ok(applicantService.findApplicants(pageable));
    }

    @Operation(summary = "서비스 신청자 검색 api", description = "서비스 신청자를 검색 유형별로 검색합니다.")
    @GetMapping("/search")
    public ResponseEntity<PageResponse> searchApplicant(
            @Parameter(description = "page만 주세요 !") @PageableDefault(sort = "id", direction = Sort.Direction.DESC, size = 10, page = 0) Pageable pageable,
            @Parameter(description = "검색유형 / 1: 신청자Id, 2:저작권자 명, 3:이메일") @RequestParam(value = "searchType", required = false) Integer searchType,
            @Parameter(description = "검색 키워드") @RequestParam(value = "keyword", required = false) String keyword)
    {
        return ResponseEntity.ok(applicantService.searchApplicant(searchType, keyword, pageable));
    }

    @Operation(summary = "서비스 신청자 상세 조회 api", description = "서비스 신청자 상세 데이터를 조회합니다.")
    @GetMapping("/{applicantId}")
    public ResponseEntity<DetailApplicantResponse> findApplicant(
            @PathVariable("applicantId") Long applicantId)
    {
        return ResponseEntity.ok(applicantService.findApplicant(applicantId));
    }

    @Operation(summary = "서비스 신청자 검토 (승인/거절/보류) api", description = "서비스 신청자 상세 데이터를 조회합니다.")
    @PatchMapping("/{applicantId}")
    public ResponseEntity reviewApplicant(
            @PathVariable("applicantId") Long applicantId,
            @Parameter(description = "0:보류, 1:승인, 2:거절 / note -> 결과에 대한 이유") @RequestBody ReviewApplicantRequest request)
    {
        applicantService.reviewApplicant(applicantId, request);

        return ResponseEntity.ok().build();
    }
}
