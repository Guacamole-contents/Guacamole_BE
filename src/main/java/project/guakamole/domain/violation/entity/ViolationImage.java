package project.guakamole.domain.violation.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class ViolationImage {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "violation_id", nullable = false)
    private Violation violation;

    @Column(name = "violation_image_url", nullable = false)
    private String url;

    public ViolationImage(Violation violation, String url) {
        this.violation = violation;
        this.url = url;
    }
}
