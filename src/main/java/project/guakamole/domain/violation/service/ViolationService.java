package project.guakamole.domain.violation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.guakamole.domain.copyright.entity.Copyright;
import project.guakamole.domain.copyright.service.CopyrightService;
import project.guakamole.domain.violation.dto.request.CreateViolationRequest;
import project.guakamole.domain.violation.dto.request.UpdateReactLevelRequest;
import project.guakamole.domain.violation.dto.response.DetailViolationResponse;
import project.guakamole.domain.violation.dto.response.FindViolationResponse;
import project.guakamole.domain.violation.entity.Violation;
import project.guakamole.domain.violation.entity.ViolationImage;
import project.guakamole.domain.violation.repository.ViolationRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ViolationService {
    private final ViolationRepository violationRepository;
    private final CopyrightService copyrightService;

    @Transactional
    public Long createViolation(CreateViolationRequest request) {
        Copyright copyright = copyrightService.findById(request.getSourceId());
        Violation violation = Violation.create(copyright, request);

        for (String imageUrl : request.getImageUrls())
            violation.addImage(new ViolationImage(violation, imageUrl));

        return violationRepository.save(violation).getId();
    }

    @Transactional(readOnly = true)
    public List<FindViolationResponse> findViolations(Pageable pageable) {
        Page<Violation> violations = violationRepository.findViolations(pageable);

        return violations.stream()
                .map(FindViolationResponse::of)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public DetailViolationResponse findViolation(Long violationId) {
        Violation violation = findById(violationId);

        return DetailViolationResponse.of(violation);
    }

    @Transactional
    public void updateReactLevel(UpdateReactLevelRequest request, Long violateId) {
        Violation violation = findById(violateId);
        violation.updateReactLevel(request.getReactLevel());
    }

    private Violation findById(Long violationId){
        return violationRepository.findById(violationId).orElseThrow(
                () -> new IllegalArgumentException("not found violation")
        );
    }
}
