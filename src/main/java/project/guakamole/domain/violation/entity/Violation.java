package project.guakamole.domain.violation.entity;

import jakarta.persistence.*;
import lombok.*;
import project.guakamole.domain.copyright.entity.Copyright;
import project.guakamole.domain.violation.dto.request.CreateViolationRequest;
import project.guakamole.global.base.BaseTimeEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Violation extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "copyright_id")
    private Copyright copyright;

    @Column(name = "violator_name", nullable = false)
    private String violatorName;

    @Column(name = "violate_date", nullable = false)
    private LocalDateTime violateDate;
    @Column(name = "violate_moment", nullable = false)
    private int violateMoment;

    @OneToMany(mappedBy = "violation", cascade = CascadeType.ALL)
    private List<ViolationImage> images = new ArrayList<>();

    @Column(name = "violate_react_level", nullable = false)
    @Enumerated(EnumType.STRING)
    private ViolateReactLevel reactLevel;

    @Builder
    public Violation(Copyright copyright, String violatorName, LocalDateTime violateDate, int violateMoment, ViolateReactLevel reactLevel) {
        this.copyright = copyright;
        this.violatorName = violatorName;
        this.violateDate = violateDate;
        this.violateMoment = violateMoment;
        this.reactLevel = reactLevel;
        this.images = images;
    }

    public static Violation create(Copyright copyright, CreateViolationRequest request) {
        return Violation.builder().
                copyright(copyright)
                .violatorName(request.getViolatorName())
                .violateDate(request.getViolateDate())
                .violateMoment(request.getViolateMoment())
                .reactLevel(ViolateReactLevel.EXAMINE)
                .build();
    }

    public void addImage(ViolationImage image){
        this.images.add(image);
    }
}
