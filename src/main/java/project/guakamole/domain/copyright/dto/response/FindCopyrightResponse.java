package project.guakamole.domain.copyright.dto.response;

import lombok.Getter;
import project.guakamole.domain.copyright.entity.Copyright;

import java.time.format.DateTimeFormatter;

@Getter
public class FindCopyrightResponse {
    private final Long sourceId;
    private final String ownerName;
    private final String copyrightName;
    private final String originalLink;
    private final String registeredDate;

    private FindCopyrightResponse(Long sourceId, String ownerName, String copyrightName, String originalLink, String registeredDate) {
        this.sourceId = sourceId;
        this.ownerName = ownerName;
        this.copyrightName = copyrightName;
        this.originalLink = originalLink;
        this.registeredDate = registeredDate;
    }

    public static FindCopyrightResponse of(final Copyright copyright) {
        return new FindCopyrightResponse(
                copyright.getId(),
                copyright.getOwnerName(),
                copyright.getCopyrightName(),
                copyright.getOriginalLink(),
                copyright.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        );
    }
}
