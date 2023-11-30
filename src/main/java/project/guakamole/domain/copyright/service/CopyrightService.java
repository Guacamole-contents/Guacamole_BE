package project.guakamole.domain.copyright.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.guakamole.domain.common.PageResponse;
import project.guakamole.domain.copyright.dto.request.CreateCopyrightRequest;
import project.guakamole.domain.copyright.dto.response.FindCopyrightResponse;
import project.guakamole.domain.copyright.entity.Copyright;
import project.guakamole.domain.copyright.repository.CopyrightRepository;
import project.guakamole.domain.copyright.searchtype.CopyrightSearchType;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CopyrightService {
    private final CopyrightRepository copyrightRepository;

    @Transactional(readOnly = true)
    public PageResponse<List<FindCopyrightResponse>> findCopyrights(Pageable pageable)
    {
        Page<Copyright> copyrightPages = copyrightRepository.findAll(pageable);

        List<FindCopyrightResponse> responses = copyrightPages.stream()
                .map(FindCopyrightResponse::of)
                .collect(Collectors.toList());

        return PageResponse.of(responses, copyrightPages);
    }

    @Transactional(readOnly = true)
    public PageResponse<List<FindCopyrightResponse>> searchCopyright(
            Integer searchType,
            String keyword,
            Pageable pageable)
    {

        Page<FindCopyrightResponse> copyrightPages = copyrightRepository.searchCopyright(CopyrightSearchType.get(searchType), keyword, pageable);

        return PageResponse.of(copyrightPages.getContent(), copyrightPages);
    }

    @Transactional
    public Long createCopyright(CreateCopyrightRequest createCopyrightRequest) {
        return copyrightRepository.save(Copyright.create(createCopyrightRequest)).getId();
    }

    @Transactional(readOnly = true)
    public Copyright findById(Long copyrightId){
        return copyrightRepository.findById(copyrightId).orElseThrow(
                () -> new IllegalArgumentException("not found copyright")
        );
    }

    @Transactional
    public void deleteCopyright(Long copyrightId) {
        Copyright copyright = findById(copyrightId);

        copyrightRepository.delete(copyright);
    }
}
