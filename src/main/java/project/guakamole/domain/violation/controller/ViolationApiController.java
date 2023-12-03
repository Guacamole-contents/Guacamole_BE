package project.guakamole.domain.violation.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import project.guakamole.domain.violation.dto.request.FindViolationFilterRequest;
import project.guakamole.domain.violation.dto.request.UpdateViolationRequest;
import project.guakamole.domain.violation.dto.response.DetailViolationResponse;
import project.guakamole.domain.violation.dto.response.FindViolationResponse;
import project.guakamole.domain.violation.service.ViolationService;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/violations")
@RequiredArgsConstructor
@Slf4j
public class ViolationApiController {
    private final ViolationService violationService;

    @PostMapping
    public ResponseEntity<Long> createViolation(
            @RequestBody CreateViolationRequest request)
    {
        Long validationId = violationService.createViolation(request);

        URI uri = URI.create("/api/violations/" + validationId);
        return ResponseEntity.created(uri).build();
    }

    @GetMapping
    public ResponseEntity<PageResponse<List<FindViolationResponse>>> findViolations(
            @PageableDefault(sort = "id", direction = Sort.Direction.DESC, size = 10, page = 0) Pageable pageable,
            @RequestBody(required = false) FindViolationFilterRequest filter)
    {

        return ResponseEntity.ok(violationService.findViolations(filter, pageable));
    }

    @GetMapping("/search")
    public ResponseEntity<PageResponse<List<FindViolationResponse>>> searchViolations(
            @PageableDefault(sort = "id", direction = Sort.Direction.DESC, size = 10, page = 0) Pageable pageable,
            @RequestParam(value = "searchType", required = false) Integer searchType,
            @RequestParam(value = "keyword", required = false) String keyword,
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") @RequestParam(value = "startDate", required = false) LocalDateTime startDate,
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") @RequestParam(value = "endDate", required = false) LocalDateTime endDate,
            @RequestParam(value = "agreementTypes", required = false) List<Integer> agreementTypes,
            @RequestParam(value = "reactLevels", required = false) List<Integer> reactLevels,
            @RequestParam(value = "minAgreementAmount", required = false) Long minAgreementAmount,
            @RequestParam(value = "maxAgreementAmount", required = false) Long maxAgreementAmount)
    {
        FilterViolationDto filter = new FilterViolationDto(startDate, endDate, agreementTypes, reactLevels, minAgreementAmount, maxAgreementAmount);
        SearchCondDto searchCond = new SearchCondDto(searchType, keyword);

        return ResponseEntity.ok(violationService.searchViolation(searchCond, filter, pageable));
    }

    @GetMapping("/{violationId}")
    public ResponseEntity<DetailViolationResponse> findViolation(
            @PathVariable("violationId") Long violationId
    )
    {
        return ResponseEntity.ok(violationService.findViolation(violationId));
    }

    @PatchMapping(value = "/{violationId}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Void> updateViolation(
            @PathVariable("violationId") Long violationId,
            @RequestPart UpdateViolationRequest request,
            @RequestPart(value = "files", required = false) MultipartFile[] files)
    {
        log.info(String.valueOf(files.length)); //파일 잘 받아오는지 확인

        violationService.updateViolationDetail(violationId, request, files);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{violationId}")
    public ResponseEntity<Void> deleteCopyright(@PathVariable("violationId") Long violationId) {
        violationService.deleteViolation(violationId);

        return ResponseEntity.noContent().build();
    }

}
