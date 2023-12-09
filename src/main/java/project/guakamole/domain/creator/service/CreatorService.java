package project.guakamole.domain.creator.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.guakamole.domain.applicant.dto.ApplicantInfoDto;
import project.guakamole.domain.common.dto.PageResponse;
import project.guakamole.domain.copyright.dto.response.FindCopyrightResponse;
import project.guakamole.domain.creator.dto.request.UpdateCreatorActiveRequest;
import project.guakamole.domain.creator.dto.response.DetailCreatorResponse;
import project.guakamole.domain.creator.dto.response.FindCreatorResponse;
import project.guakamole.domain.creator.entity.Creator;
import project.guakamole.domain.creator.entity.CreatorActiveStatus;
import project.guakamole.domain.creator.repository.CreatorRepository;
import project.guakamole.domain.creator.searchType.CreatorSearchType;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CreatorService {
    private final CreatorRepository creatorRepository;

    @Transactional
    public void create(ApplicantInfoDto applicantInfoDto) {
        Creator creator = Creator.create(applicantInfoDto);

        creatorRepository.save(creator);
    }

    @Transactional(readOnly = true)
    public PageResponse<List<FindCreatorResponse>> findCreators(Pageable pageable) {
        Page<Creator> creatorPages = creatorRepository.findAll(pageable);

        List<FindCreatorResponse> responses = creatorPages.stream()
                .map(FindCreatorResponse::of)
                .collect(Collectors.toList());

        return PageResponse.of(responses, creatorPages);
    }

    @Transactional(readOnly = true)
    public PageResponse<List<FindCreatorResponse>> searchCreator(Integer searchType, String keyword, Pageable pageable) {
        Page<FindCreatorResponse> responses = creatorRepository.searchCreator(CreatorSearchType.get(searchType), keyword, pageable);

        return PageResponse.of(responses.getContent(), responses);
    }

    @Transactional(readOnly = true)
    public DetailCreatorResponse findCreator(Long creatorId) {
        Creator creator = findById(creatorId);

        return DetailCreatorResponse.of(creator);
    }

    @Transactional
    public void updateActive(Long creatorId, UpdateCreatorActiveRequest request) {
        Creator creator = findById(creatorId);

        CreatorActiveStatus activeStatus = CreatorActiveStatus.get(request.getActive());

        if(creator.getActiveStatus() != activeStatus)
            creator.updateActive(activeStatus);
    }

    private Creator findById(Long id) {
        return creatorRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("not found creator")
        );
    }
}
