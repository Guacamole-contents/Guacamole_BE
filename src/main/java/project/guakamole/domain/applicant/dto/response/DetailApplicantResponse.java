package project.guakamole.domain.applicant.dto.response;

import lombok.Getter;
import project.guakamole.domain.applicant.entity.Applicant;
import project.guakamole.domain.applicant.entity.ApplicantAuthenticationImage;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class DetailApplicantResponse {
    private Long id;
    private Long userId;
    private String creatorName;
    private String email;
    private List<String> imageUrls;
    private String chanelLink;
    private String approveStatus;
    private String note;

    public DetailApplicantResponse(Long id, Long userId, String creatorName, String email, List<String> imageUrls, String chanelLink, String approveStatus, String note) {
        this.id = id;
        this.userId = userId;
        this.creatorName = creatorName;
        this.email = email;
        this.imageUrls = imageUrls;
        this.chanelLink = chanelLink;
        this.approveStatus = approveStatus;
        this.note = note;
    }

    public static DetailApplicantResponse of(Applicant applicant){
        List<String> imageUrls = applicant.getImages().stream().map(ApplicantAuthenticationImage::getUrl).collect(Collectors.toList());

        return new DetailApplicantResponse(
                applicant.getId(),
                applicant.getUserId(),
                applicant.getCreatorName(),
                applicant.getEmail(),
                imageUrls,
                applicant.getChanelLink(),
                applicant.getApproveStatus().getValue(),
                applicant.getNote()
        );
    }


}
