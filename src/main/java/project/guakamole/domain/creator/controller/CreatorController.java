package project.guakamole.domain.creator.controller;

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

@RestController
@RequestMapping("/api/creators")
@RequiredArgsConstructor
public class CreatorController {

    private final CreatorService creatorService;

    @GetMapping
    public ResponseEntity<PageResponse> findCreators(
            @PageableDefault(sort = "id", direction = Sort.Direction.DESC, size = 10, page = 0) Pageable pageable)
    {
        return ResponseEntity.ok(creatorService.findCreators(pageable));
    }

    @GetMapping("/search")
    public ResponseEntity<PageResponse> searchCreator(
            @PageableDefault(sort = "id", direction = Sort.Direction.DESC, size = 10, page = 0) Pageable pageable,
            @RequestParam(value = "searchType", required = false) Integer searchType,
            @RequestParam(value = "keyword", required = false) String keyword)
    {
        return ResponseEntity.ok(creatorService.searchCreator(searchType, keyword, pageable));
    }

    @GetMapping("/{creatorId}")
    public ResponseEntity<DetailCreatorResponse> findCreator(
            @PathVariable("creatorId") Long creatorId)
    {
        return ResponseEntity.ok(creatorService.findCreator(creatorId));
    }

    @PatchMapping("/{creatorId}")
    public ResponseEntity<Void> updateCreatorActive(
            @PathVariable("creatorId") Long creatorId,
            @RequestBody UpdateCreatorActiveRequest request)
    {
        creatorService.updateActive(creatorId, request);

        return ResponseEntity.noContent().build();
    }
}
