package project.guakamole.domain.creator.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import project.guakamole.domain.applicant.dto.ApplicantInfoDto;
import project.guakamole.global.base.BaseTimeEntity;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@SQLDelete(sql = "UPDATE creator SET deleted_date = CURRENT_TIMESTAMP WHERE id = ?")
@Where(clause = "deleted_date is null")
public class Creator extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "chanel_name", nullable = false)
    private String name;
    @Column(name = "email", nullable = false)
    private String email;
    @Column(name = "copyright_count", nullable = false)
    private Integer copyrightCount;
    @Column(name = "chanel_link", nullable = false)
    private String chanelLink;
    @Column(name = "active_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private CreatorActiveStatus activeStatus;

    @Builder
    public Creator(String name, String email, Integer copyrightCount, String chanelLink, CreatorActiveStatus activeStatus) {
        this.name = name;
        this.email = email;
        this.copyrightCount = copyrightCount;
        this.chanelLink = chanelLink;
        this.activeStatus = activeStatus;
    }

    public static Creator create(ApplicantInfoDto dto){
        return Creator.builder()
                .name(dto.getCreatorName())
                .email(dto.getEmail())
                .copyrightCount(0)
                .chanelLink(dto.getChanelLink())
                .activeStatus(CreatorActiveStatus.ON)
                .build()
                ;
    }

    public void updateActive(CreatorActiveStatus activeStatus) {
        this.activeStatus = activeStatus;
    }
}
