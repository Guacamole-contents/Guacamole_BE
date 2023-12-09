package project.guakamole.domain.violation.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.guakamole.global.base.BaseTimeEntity;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ViolationContractFile extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "violation_id", nullable = false)
    private Violation violation;

    @Column(name = "original_file_name", nullable = false)
    private String originalFileName;
    @Column(name = "url", nullable = false)
    private String url;

    public ViolationContractFile(Violation violation, String originalFileName, String url) {
        this.violation = violation;
        this.originalFileName = originalFileName;
        this.url = url;
    }
}
