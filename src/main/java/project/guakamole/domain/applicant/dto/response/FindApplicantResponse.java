package project.guakamole.domain.applicant.dto.response;

import lombok.Getter;
import project.guakamole.domain.applicant.entity.Applicant;

@Getter
public class FindApplicantResponse {
    private Long id;
    private String chanelName;
    private String email;

    public FindApplicantResponse(Long id, String chanelName, String email) {
        this.id = id;
        this.chanelName = chanelName;
        this.email = email;
    }

    public static FindApplicantResponse of(Applicant applicant) {
        return new FindApplicantResponse(
                applicant.getId(),
                applicant.getCreatorName(),
                applicant.getEmail()
        );
    }
}
