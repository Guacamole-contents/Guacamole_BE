package project.guakamole.domain.violation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.guakamole.domain.violation.dto.request.CreateViolationRequest;
import project.guakamole.domain.violation.dto.response.FindViolationResponse;
import project.guakamole.domain.violation.service.ViolationService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ViolationApiController {
    private final ViolationService violationService;

    @PostMapping("/violations")
    public ResponseEntity<Long> createViolation(@RequestBody CreateViolationRequest request){
        Long validationId = violationService.createViolation(request);

        URI uri = URI.create("/api/violation/" + validationId);
        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/violations")
    public ResponseEntity<List<FindViolationResponse>> findViolations(@RequestParam(name = "page", defaultValue = "0") Integer page){
        Pageable pageable = PageRequest.of(page, 10);

        return ResponseEntity.ok(violationService.findViolations(pageable));
    }
}
