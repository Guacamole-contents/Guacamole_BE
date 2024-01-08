package project.guakamole.domain.creator.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import project.guakamole.domain.creator.entity.Creator;

@Getter
@AllArgsConstructor
public class FindCreatorResponse {

    private final Long creatorId;
    private final String creatorName;
    private final String email;
    private final Integer copyrightCount;
    private final String active;

    public static FindCreatorResponse of(Creator creator){
        return new FindCreatorResponse(
                creator.getId(),
                creator.getName(),
                creator.getEmail(),
                creator.getCopyrightCount(),
                creator.getActiveStatus().getValue()
        );
    }
}
