package project.guakamole.domain.applicant.entity;

import jakarta.persistence.*;
import lombok.*;
import project.guakamole.global.base.BaseTimeEntity;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Applicant extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "chanel_name", nullable = false)
    private String chanelName;
    @Column(name = "email", nullable = false)
    private String email;
    @Column(name = "chanel_link", nullable = false)
    private String chanelLink;
    @OneToMany(mappedBy = "applicant", cascade = CascadeType.ALL)
    private List<ChanelAuthenticationImage> images = new ArrayList<>();
    @Column(name = "approve_status", nullable = false)
    private ApplicantApproveStatus approveStatus;
    @Column(name = "note", columnDefinition = "")
    private String note = ""; //승인 또는 거절시 텍스트 입력란

    @Builder
    public Applicant(String chanelName, String email, String chanelLink, ApplicantApproveStatus approveStatus) {
        this.chanelName = chanelName;
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

