package project.guakamole.domain.applicant.dto.response;

import lombok.Getter;
import project.guakamole.domain.applicant.entity.Applicant;

@Getter
public class FindApplicantResponse {
    private Long id;
    private Long userId;
    private String chanelName;
    private String email;
    private String approveStatus;

    public FindApplicantResponse(Long id,Long userId, String chanelName, String email, String approveStatus) {
        this.id = id;
        this.userId = userId;
        this.chanelName = chanelName;
        this.email = email;
        this.approveStatus = approveStatus;
    }

    public static FindApplicantResponse of(Applicant applicant) {
        return new FindApplicantResponse(
                applicant.getId(),
                applicant.getUserId(),
                applicant.getCreatorName(),
                applicant.getEmail(),
                applicant.getApproveStatus().getValue()
        );
    }
}
