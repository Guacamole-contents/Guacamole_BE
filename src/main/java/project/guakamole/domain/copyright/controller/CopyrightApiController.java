package project.guakamole.domain.copyright.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import project.guakamole.domain.copyright.dto.response.FindCopyrightResponse;
import project.guakamole.domain.copyright.service.CopyrightService;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CopyrightApiController {
    private final CopyrightService copyrightService;

    @GetMapping("/copyrights")
    public ResponseEntity<List<FindCopyrightResponse>> findCopyrights(@RequestParam(name = "page", defaultValue = "0") Integer page){
        Pageable pageable = PageRequest.of(page, 10);

        return ResponseEntity.ok(copyrightService.findCopyrights(pageable));
    }
}
