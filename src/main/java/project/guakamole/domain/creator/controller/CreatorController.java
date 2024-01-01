package project.guakamole.domain.creator.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.guakamole.domain.common.dto.PageResponse;
import project.guakamole.domain.creator.dto.request.UpdateCreatorActiveRequest;
import project.guakamole.domain.creator.dto.response.DetailCreatorResponse;
import project.guakamole.domain.creator.service.CreatorService;
@Tag(name = "저작권자 컨트롤러", description = " 저작권자(서비스 이용자) 관련 컨트롤러 입니다.")
//@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/creators")
@RequiredArgsConstructor
public class CreatorController {

    private final CreatorService creatorService;

    @Operation(summary = "저작권자 목록 조회 api", description = "저작권자(서비스 이용자) 데이터 목록을 조회합니다.")
    @GetMapping
    public ResponseEntity<PageResponse> findCreators(
            @Parameter(description = "page만 주세요!") @PageableDefault(sort = "id", direction = Sort.Direction.DESC, size = 10, page = 0) Pageable pageable)
    {
        return ResponseEntity.ok(creatorService.findCreators(pageable));
    }

    @Operation(summary = "저작권자 검색 api", description = "저작권자(서비스 이용자) 데이터를 검색 유형에 따라 검색합니다.")
    @GetMapping("/search")
    public ResponseEntity<PageResponse> searchCreator(
            @Parameter(description = "page만 주세요!") @PageableDefault(sort = "id", direction = Sort.Direction.DESC, size = 10, page = 0) Pageable pageable,
            @Parameter(description = "검색유형 / 1:저작권자Id, 2:저작권자 명, 3:이메일") @RequestParam(value = "searchType", required = false) Integer searchType,
            @RequestParam(value = "keyword", required = false) String keyword)
    {
        return ResponseEntity.ok(creatorService.searchCreator(searchType, keyword, pageable));
    }

    @Operation(summary = "저작권자 상세 조회 api", description = "저작권자 상세 데이터를 조회합니다.")
    @GetMapping("/{creatorId}")
    public ResponseEntity<DetailCreatorResponse> findCreator(
            @PathVariable("creatorId") Long creatorId)
    {
        return ResponseEntity.ok(creatorService.findCreator(creatorId));
    }

    @Operation(summary = "저작권자(유저) 권한 수정 api", description = "서비스 이용자의 상태를 관리합니다.")
    @PatchMapping("/{creatorId}")
    public ResponseEntity<Void> updateCreatorActive(
            @PathVariable("creatorId") Long creatorId,
            @Parameter(description = "1: 정상 이용, 2: 사용 정지")@RequestBody UpdateCreatorActiveRequest request)
    {
        creatorService.updateActive(creatorId, request);

        return ResponseEntity.noContent().build();
    }
}
