package project.guakamole.domain.violation.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import project.guakamole.domain.common.dto.PageResponse;
import project.guakamole.domain.common.dto.SearchCondDto;
import project.guakamole.domain.violation.dto.FilterViolationDto;
import project.guakamole.domain.violation.dto.request.CreateViolationRequest;
import project.guakamole.domain.violation.dto.request.UpdateViolationRequest;
import project.guakamole.domain.violation.dto.response.DetailViolationResponse;
import project.guakamole.domain.violation.dto.response.FindViolationResponse;
import project.guakamole.domain.violation.service.ViolationService;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

@Tag(name = "침해 컨트롤러", description = " 침해 관련 컨트롤러 입니다.")
//@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/violations")
@RequiredArgsConstructor
public class ViolationApiController {
    private final ViolationService violationService;

    @Operation(summary = "침해 데이터 생성 api", description = "침해 데이터를 생성합니다.")
    @ApiResponse(responseCode = "201", description = "데이터 생성에 성공하였고, Header Location 에 리소스 접근 url이 있습니다.")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Long> createViolation(
            @Parameter(description = "application/json 형식으로 침해 데이터 생성에 필요한 값을 받습니다.") @RequestPart @Valid CreateViolationRequest request,
            @Parameter(description = "multipart/form-data 형식의 이미지 리스트를 받습니다.") @RequestPart(value = "files", required = true) List<MultipartFile> files) {
        Long validationId = violationService.createViolation(request, files);

        URI uri = URI.create("/api/violations/" + validationId);
        return ResponseEntity.created(uri).build();
    }

    @Operation(summary = "침해 데이터 조회 api", description = "침해 데이터를 조회합니다. 필터링 적용도 가능합니다.")
    @GetMapping
    public ResponseEntity<PageResponse<List<FindViolationResponse>>> findViolations(
            @Parameter(description = "page 값만 주세요", required = true) @PageableDefault(sort = "id", direction = Sort.Direction.DESC, size = 10, page = 0) Pageable pageable,
            @Parameter(description = "시작일 필터", example = "yyyy-MM-dd HH:mm:ss") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") @RequestParam(value = "startDate", required = false) LocalDateTime startDate,
            @Parameter(description = "종료일 필터", example = "yyyy-MM-dd HH:mm:ss") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") @RequestParam(value = "endDate", required = false) LocalDateTime endDate,
            @Parameter(description = "합의유형 필터(array타입) 1:보류, 2:침해합의, 3:삭제요청, 4:법적대응") @RequestParam(value = "agreementTypes", required = false) List<Integer> agreementTypes,
            @Parameter(description = "대응단계 필터 (array타입) 1:저작권자검토, 2:침해대응중, 3:대응완료") @RequestParam(value = "reactLevels", required = false) List<Integer> reactLevels,
            @Parameter(description = "최소금액 필터 (≤)") @RequestParam(value = "minAgreementAmount", required = false) Long minAgreementAmount,
            @Parameter(description = "최대금액 필터 (≥)") @RequestParam(value = "maxAgreementAmount", required = false) Long maxAgreementAmount) {
        FilterViolationDto filter = new FilterViolationDto(startDate, endDate, agreementTypes, reactLevels, minAgreementAmount, maxAgreementAmount);

        return ResponseEntity.ok(violationService.findViolations(filter, pageable));
    }

    @Operation(summary = "침해 데이터 검색 api", description = "침해 데이터를 검색합니다. 필터링 적용도 가능합니다.")
    @GetMapping("/search")
    public ResponseEntity<PageResponse<List<FindViolationResponse>>> searchViolations(
            @Parameter(description = "page 값만 주세요", required = true) @PageableDefault(sort = "id", direction = Sort.Direction.DESC, size = 10, page = 0) Pageable pageable,
            @Parameter(description = "검색 유형 / 1:sourceId, 2:저작권자 명") @RequestParam(value = "searchType", required = false) Integer searchType,
            @Parameter(description = "검색 키워드") @RequestParam(value = "keyword", required = false) String keyword,
            @Parameter(description = "시작일 필터") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") @RequestParam(value = "startDate", required = false) LocalDateTime startDate,
            @Parameter(description = "종료일 필터") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") @RequestParam(value = "endDate", required = false) LocalDateTime endDate,
            @Parameter(description = "합의유형 필터(array타입) 1:보류, 2:침해합의, 3:삭제요청, 4:법적대응") @RequestParam(value = "agreementTypes", required = false) List<Integer> agreementTypes,
            @Parameter(description = "대응단계 필터 (array타입) 1:저작권자검토, 2:침해대응중, 3:대응완료") @RequestParam(value = "reactLevels", required = false) List<Integer> reactLevels,
            @Parameter(description = "최소 합의 금액") @RequestParam(value = "minAgreementAmount", required = false) Long minAgreementAmount,
            @Parameter(description = "최대 합의 금액") @RequestParam(value = "maxAgreementAmount", required = false) Long maxAgreementAmount) {
        FilterViolationDto filter = new FilterViolationDto(startDate, endDate, agreementTypes, reactLevels, minAgreementAmount, maxAgreementAmount);
        SearchCondDto searchCond = new SearchCondDto(searchType, keyword);

        return ResponseEntity.ok(violationService.searchViolation(searchCond, filter, pageable));
    }

    @Operation(summary = "침해 데이터 상세 조회 api", description = "침해 데이터를 눌러서 상세 조회합니다.")
    @GetMapping("/{violationId}")
    public ResponseEntity<DetailViolationResponse> findViolation(
            @PathVariable("violationId") Long violationId) {
        return ResponseEntity.ok(violationService.findViolation(violationId));
    }

    @Operation(summary = "침해 데이터 수정 api", description = "침해 상세 페이지에 있는 결제링크, 대응단계, 합의서 등록 등 정보를 수정합니다.")
    @PatchMapping(value = "/{violationId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity updateViolation(
            @PathVariable("violationId") Long violationId,
            @Parameter(description = "application/json 형식으로 결제링크 or 대응단계를 변경할 수 있습니다. \" 1: 저작권자 검토, 2: 침해 대응중, 3: 대응 완료\"") @RequestPart @Valid UpdateViolationRequest request,
            @Parameter(description = "multipart/form-data 형식으로 합의서 파일을 받습니다.") @RequestPart(value = "files", required = false) List<MultipartFile> files) {
        violationService.updateViolationDetail(violationId, request, files);

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "침해 데이터 삭제 api", description = "침해 데이터를 삭제합니다.")
    @DeleteMapping("/{violationId}")
    public ResponseEntity<Void> deleteCopyright(@PathVariable("violationId") Long violationId) {
        violationService.deleteViolation(violationId);

        return ResponseEntity.noContent().build();
    }

}
