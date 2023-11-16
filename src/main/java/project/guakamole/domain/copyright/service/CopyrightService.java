package project.guakamole.domain.copyright.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.guakamole.domain.copyright.dto.request.CreateCopyrightRequest;
import project.guakamole.domain.copyright.dto.response.FindCopyrightResponse;
import project.guakamole.domain.copyright.entity.Copyright;
import project.guakamole.domain.copyright.repository.CopyrightRepository;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CopyrightService {
    private final CopyrightRepository copyrightRepository;

    @Transactional(readOnly = true)
    public List<FindCopyrightResponse> findCopyrights(Pageable pageable) {
        Page<Copyright> findCopyrights = copyrightRepository.findCopyrights(pageable);
        return findCopyrights.stream()
                .map(FindCopyrightResponse::of)
                .collect(Collectors.toList());
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
}
