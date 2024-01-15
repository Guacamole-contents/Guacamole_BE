package project.guakamole.domain.creator.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import project.guakamole.domain.creator.entity.Creator;

@Getter
@AllArgsConstructor
public class DetailCreatorResponse {
    private final Long creatorId;
    private final Long userId;
    private final String creatorName;
    private final Integer copyrightCount;
    private final String email;
    private final String chanelLink;
    private final String active;

    public static DetailCreatorResponse of(Creator creator) {
        return new DetailCreatorResponse(
                creator.getId(),
                creator.getUserId(),
                creator.getName(),
                creator.getCopyrightCount(),
                creator.getEmail(),
                creator.getChanelLink(),
                creator.getActiveStatus().getValue()
        );
    }
}
