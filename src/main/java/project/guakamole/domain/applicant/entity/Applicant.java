package project.guakamole.domain.applicant.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import project.guakamole.global.base.BaseTimeEntity;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE applicant SET deleted_date = CURRENT_TIMESTAMP WHERE id = ?")
@Where(clause = "deleted_date is null")
public class Applicant extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "userId") //nullable = false 로 추후에 변경
    private Long userId;
    @Column(name = "creator_name", nullable = false)
    private String creatorName;
    @Column(name = "email", nullable = false)
    private String email;
    @Column(name = "chanel_link", nullable = false)
    private String chanelLink;
    @OneToMany(mappedBy = "applicant", cascade = CascadeType.ALL)
    private List<ApplicantAuthenticationImage> images = new ArrayList<>();
    @Column(name = "approve_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private ApplicantApproveStatus approveStatus;
    @Column(name = "note", columnDefinition = "")
    private String note = ""; //승인 또는 거절시 텍스트 입력란

    @Builder
    public Applicant(String creatorName, String email, String chanelLink, ApplicantApproveStatus approveStatus) {
        this.creatorName = creatorName;
        this.email = email;
        this.chanelLink = chanelLink;
        this.approveStatus = approveStatus;
    }

    public void updateApproveStatus(ApplicantApproveStatus approveStatus){
        this.approveStatus = approveStatus;
    }

    public void updateNote(String note){
        this.note = note;
    }
}

