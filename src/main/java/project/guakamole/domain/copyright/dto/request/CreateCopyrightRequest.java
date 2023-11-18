package project.guakamole.domain.copyright.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateCopyrightRequest {
    @NotBlank(message = "저작권자 이름은 필수 데이터입니다.")
    private String ownerName;
    @NotBlank(message = "저작권명은 필수 데이터입니다.")
    private String copyrightName;
    @NotBlank(message = "저작권 링크는 필수 데이터입니다.")
    private String originalLink;
}
