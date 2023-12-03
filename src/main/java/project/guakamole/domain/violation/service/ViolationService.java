package project.guakamole.domain.violation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import project.guakamole.domain.common.dto.PageResponse;
import project.guakamole.domain.common.dto.SearchCondDto;
import project.guakamole.domain.copyright.entity.Copyright;
import project.guakamole.domain.copyright.service.CopyrightService;
import project.guakamole.domain.violation.dto.FilterViolationDto;
import project.guakamole.domain.violation.dto.request.CreateViolationRequest;
import project.guakamole.domain.violation.dto.request.FindViolationFilterRequest;
import project.guakamole.domain.violation.dto.request.UpdateViolationRequest;
import project.guakamole.domain.violation.dto.response.DetailViolationResponse;
import project.guakamole.domain.violation.dto.response.FindViolationResponse;
import project.guakamole.domain.violation.entity.Violation;
import project.guakamole.domain.violation.entity.ViolationImage;
import project.guakamole.domain.violation.repository.ViolationRepository;
import project.guakamole.domain.violation.searchtype.ViolationSearchType;

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

    @Transactional
    public void deleteViolation(Long violationId) {
        Violation violation = findById(violationId);

        violationRepository.delete(violation);
    }

    @Transactional(readOnly = true)
    public PageResponse<List<FindViolationResponse>> findViolations(
            FindViolationFilterRequest filter,
            Pageable pageable)
    {
        Page<Violation> violationPage = violationRepository.findViolationWithFilter(pageable);

        List<FindViolationResponse> response = violationPage.stream()
                .map(FindViolationResponse::of)
                .collect(Collectors.toList());

        return PageResponse.of(response, violationPage);

    }

    public PageResponse<List<FindViolationResponse>> searchViolation(
            SearchCondDto searchCond,
            FilterViolationDto filter,
            Pageable pageable)
    {
        Page<FindViolationResponse> violationPages = violationRepository.searchViolationWithFilter(
                ViolationSearchType.get(searchCond.getSearchType()),
                searchCond.getKeyword(),
                filter,
                pageable);

        return PageResponse.of(violationPages.getContent(), violationPages);
    }

    @Transactional(readOnly = true)
    public DetailViolationResponse findViolation(Long violationId) {
        Violation violation = findById(violationId);

        return DetailViolationResponse.of(violation);
    }

    @Transactional
    public void updateViolationDetail(Long violationId, UpdateViolationRequest request, MultipartFile[] files) {
        Violation violation = findById(violationId);

        violation.updateReactLevel(request.getReactLevel());
        violation.updatePaymentLink(request.getPaymentLink());

        //MultipartFile 처리 로직
    }

    private Violation findById(Long violationId){
        return violationRepository.findById(violationId).orElseThrow(
                () -> new IllegalArgumentException("not found violation")
        );
    }
}
