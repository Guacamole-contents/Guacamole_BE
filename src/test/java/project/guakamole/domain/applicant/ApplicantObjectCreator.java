package project.guakamole.domain.applicant;

import project.guakamole.domain.applicant.entity.Applicant;
import project.guakamole.domain.applicant.entity.ApplicantApproveStatus;

public class ApplicantObjectCreator {

    public static Applicant applicant(String creatorName, String email, String chanelLink, ApplicantApproveStatus status){
        return Applicant.builder()
                .creatorName(creatorName)
                .email(email)
                .chanelLink(chanelLink)
                .approveStatus(status)
                .build();
    }
}
