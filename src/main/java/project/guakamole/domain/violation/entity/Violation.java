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

    @Column(name = "violate_link", nullable = false)
    private String violateLink;

    @OneToMany(mappedBy = "violation", cascade = CascadeType.ALL)
    private List<ViolationImage> images = new ArrayList<>();

    @Column(name = "violate_react_level", nullable = false)
    @Enumerated(EnumType.STRING)
    private ViolateReactLevel reactLevel;

    @Column(name = "agreement_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private AgreementType agreementType;

    @Column(name = "agreement_amount", nullable = false)
    private Long agreementAmount;

    @Column(name = "contract_url")
    private String contractUrl;

    @Builder
    public Violation(
            Copyright copyright,
            String violatorName,
            LocalDateTime violateDate,
            int violateMoment,
            ViolateReactLevel reactLevel,
            String violateLink,
            AgreementType agreementType,
            Long agreementAmount)
    {
        this.copyright = copyright;
        this.violatorName = violatorName;
        this.violateDate = violateDate;
        this.violateMoment = violateMoment;
        this.reactLevel = reactLevel;
        this.violateLink = violateLink;
        this.agreementType = agreementType;
        this.agreementAmount = agreementAmount;
    }

    public static Violation create(
            Copyright copyright,
            CreateViolationRequest request)
    {
        return Violation.builder().
                copyright(copyright)
                .violatorName(request.getViolatorName())
                .violateDate(request.getViolateDate())
                .violateMoment(request.getViolateMoment())
                .violateLink(request.getLink())
                .reactLevel(ViolateReactLevel.EXAMINE) //초기 검토중
                .agreementType(AgreementType.HOLD)
                .agreementAmount(0L)
                .build();
    }

    public void addImage(ViolationImage image){
        this.images.add(image);
    }

    public void updateReactLevel(String reactLevel) {
        if(reactLevel.equals("EXAMINE")) this.reactLevel = ViolateReactLevel.EXAMINE;
        else if(reactLevel.equals("REACT")) this.reactLevel = ViolateReactLevel.REACT;
        else if(reactLevel.equals("COMPLETED")) this.reactLevel = ViolateReactLevel.COMPLETED;

        else
            return;
    }
}
