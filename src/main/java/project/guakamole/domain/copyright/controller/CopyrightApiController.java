package project.guakamole.domain.copyright.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.guakamole.domain.common.dto.PageResponse;
import project.guakamole.domain.copyright.dto.request.CreateCopyrightRequest;
import project.guakamole.domain.copyright.service.CopyrightService;

import java.net.URI;
@Tag(name = "저작물 컨트롤러", description = " 저작물 관련 컨트롤러 입니다.")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/copyrights")
@RequiredArgsConstructor
public class CopyrightApiController {
    private final CopyrightService copyrightService;

    @Operation(summary = "저작권 데이터 생성 api", description = "저작권 데이터를 생성합니다.")
    @ApiResponse(responseCode = "201", description = "Header 에 location에 리소스 접근 url이 있습니다.")
    @PostMapping
    public ResponseEntity<Long> createCopyright(@RequestBody CreateCopyrightRequest request) {
        Long copyrightId = copyrightService.createCopyright(request);

        URI uri = URI.create("/api/copyrights/" + copyrightId);
        return ResponseEntity.created(uri).build();
    }

    @Operation(summary = "저작권 데이터 목록 조회 api", description = "저작권 데이터를 조회합니다.")
    @GetMapping
    public ResponseEntity<PageResponse> findCopyrights(
            @Parameter(description = "page만 주세요 !") @PageableDefault(sort = "id", direction = Sort.Direction.DESC, size = 10, page = 0) Pageable pageable) {

        return ResponseEntity.ok(copyrightService.findCopyrights(pageable));
    }

    @Operation(summary = "저작권 데이터 검색 api", description = "저작권 데이터를 검색합니다.")
    @GetMapping("/search")
    public ResponseEntity<PageResponse> searchCopyrights(
            @Parameter(description = "page만 주세요 !") @PageableDefault(sort = "id", direction = Sort.Direction.DESC, size = 10, page = 0) Pageable pageable,
            @Parameter(description = "검색유형 / 1: 저작물ID, 2:저작물명, 3:저작권자명") @RequestParam(value = "searchType", required = false) Integer searchType,
            @Parameter(description = "검색 키워드")@RequestParam(value = "keyword", required = false) String keyword) {

        return ResponseEntity.ok(copyrightService.searchCopyright(searchType, keyword, pageable));
    }

    @Operation(summary = "저작권 데이터 삭제 api", description = "저작권 데이터를 삭제합니다.")
    @DeleteMapping("/{copyrightId}")
    public ResponseEntity<Void> deleteCopyright(@PathVariable("copyrightId") Long copyrightId) {
        copyrightService.deleteCopyright(copyrightId);

        return ResponseEntity.noContent().build();
    }
}
