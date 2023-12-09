package project.guakamole.domain.applicant.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.guakamole.global.base.BaseTimeEntity;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ApplicantAuthenticationImage extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "applicant_id", nullable = false)
    private Applicant applicant;

    @Column(name = "original_name", nullable = false)
    private String originalName;
    @Column(name = "url", nullable = false)
    private String url;

    public ApplicantAuthenticationImage(Applicant applicant, String originalName, String url) {
        this.applicant = applicant;
        this.originalName = originalName;
        this.url = url;
    }
}
