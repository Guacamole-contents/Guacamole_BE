package project.guakamole.domain.copyright.controller;

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

@RestController
@RequestMapping("/api/copyrights")
@RequiredArgsConstructor
public class CopyrightApiController {
    private final CopyrightService copyrightService;

    @PostMapping
    public ResponseEntity<Long> createCopyright(@RequestBody CreateCopyrightRequest request) {
        Long copyrightId = copyrightService.createCopyright(request);

        URI uri = URI.create("/api/copyrights/" + copyrightId);
        return ResponseEntity.created(uri).build();
    }

    @GetMapping
    public ResponseEntity<PageResponse> findCopyrights(
            @PageableDefault(sort = "id", direction = Sort.Direction.DESC, size = 10, page = 0) Pageable pageable) {

        return ResponseEntity.ok(copyrightService.findCopyrights(pageable));
    }

    @GetMapping("/search")
    public ResponseEntity<PageResponse> searchCopyrights(
            @PageableDefault(sort = "id", direction = Sort.Direction.DESC, size = 10, page = 0) Pageable pageable,
            @RequestParam(value = "searchType", required = false) Integer searchType,
            @RequestParam(value = "keyword", required = false) String keyword) {

        return ResponseEntity.ok(copyrightService.searchCopyright(searchType, keyword, pageable));
    }

    @DeleteMapping("/{copyrightId}")
    public ResponseEntity<Void> deleteCopyright(@PathVariable("copyrightId") Long copyrightId) {
        copyrightService.deleteCopyright(copyrightId);

        return ResponseEntity.noContent().build();
    }
}
