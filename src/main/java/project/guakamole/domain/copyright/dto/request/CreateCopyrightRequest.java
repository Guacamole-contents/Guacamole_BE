package project.guakamole.domain.copyright.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateCopyrightRequest {
    private String ownerName;
    private String copyrightName;
    private String originalLink;
}
