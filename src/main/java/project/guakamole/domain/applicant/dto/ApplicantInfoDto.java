package project.guakamole.domain.applicant.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApplicantInfoDto {
    private Long userId;
    private String creatorName;
    private String email;
    private String chanelLink;
}
