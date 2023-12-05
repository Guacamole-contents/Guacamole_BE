package project.guakamole.domain.applicant.dto.response;

import lombok.Getter;
import project.guakamole.domain.applicant.entity.Applicant;
import project.guakamole.domain.applicant.entity.ApplicantAuthenticationImage;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class DetailApplicantResponse {
    private Long id;
    private String creatorName;
    private String email;
    private List<String> imageUrls;
    private String chanelLink;
    private String note;

    public DetailApplicantResponse(Long id, String creatorName, String email, List<String> imageUrls, String chanelLink, String note) {
        this.id = id;
        this.creatorName = creatorName;
        this.email = email;
        this.imageUrls = imageUrls;
        this.chanelLink = chanelLink;
        this.note = note;
    }

    public static DetailApplicantResponse of(Applicant applicant){
        List<String> imageUrls = applicant.getImages().stream().map(ApplicantAuthenticationImage::getUrl).collect(Collectors.toList());

        return new DetailApplicantResponse(
                applicant.getId(),
                applicant.getCreatorName(),
                applicant.getEmail(),
                imageUrls,
                applicant.getChanelLink(),
                applicant.getNote()
        );
    }


}
