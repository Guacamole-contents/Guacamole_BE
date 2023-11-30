package project.guakamole.domain.copyright.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import project.guakamole.domain.copyright.entity.Copyright;

import java.time.LocalDateTime;

@Getter
public class FindCopyrightResponse {
    private final Long sourceId;
    private final String copyrightName;
    private final String ownerName;
    private final String originalLink;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private final LocalDateTime registeredDate;

    public FindCopyrightResponse(Long sourceId, String copyrightName, String ownerName, String originalLink, LocalDateTime registeredDate) {
        this.sourceId = sourceId;
        this.copyrightName = copyrightName;
        this.ownerName = ownerName;
        this.originalLink = originalLink;
        this.registeredDate = registeredDate;
    }

    public static FindCopyrightResponse of(final Copyright copyright) {
        return new FindCopyrightResponse(
                copyright.getId(),
                copyright.getOwnerName(),
                copyright.getCopyrightName(),
                copyright.getOriginalLink(),
                copyright.getCreatedDate()
        );
    }
}
