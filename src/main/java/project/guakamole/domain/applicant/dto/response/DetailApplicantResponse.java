package project.guakamole.domain.applicant.dto.response;

import lombok.Getter;
import project.guakamole.domain.applicant.entity.Applicant;

@Getter
public class DetailApplicantResponse {
    private Long id;
    private String chanelName;
    private String email;
    private String chanelLink;
    private String note;

    public DetailApplicantResponse(Long id, String chanelName, String email, String chanelLink,String note) {
        this.id = id;
        this.chanelName = chanelName;
        this.email = email;
        this.chanelLink = chanelLink;
        this.note = note;
    }

    public static DetailApplicantResponse of(Applicant applicant){
        return new DetailApplicantResponse(
                applicant.getId(),
                applicant.getChanelName(),
                applicant.getEmail(),
                applicant.getChanelLink(),
                applicant.getNote()
        );
    }
}
