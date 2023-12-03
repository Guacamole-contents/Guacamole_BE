package project.guakamole.domain.violation.entity;

import com.querydsl.core.util.StringUtils;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import project.guakamole.domain.copyright.entity.Copyright;
import project.guakamole.domain.violation.dto.request.CreateViolationRequest;
import project.guakamole.global.base.BaseTimeEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE violation SET deleted_date = CURRENT_TIMESTAMP WHERE id = ?")
@Where(clause = "deleted_date is null")
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
    private Integer violateMoment;

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

    @Column(name = "deleted_date")
    private LocalDateTime deletedDate;

    @Column(name = "agreement_payment_link")
    private String agreementPaymentLink;

    @Builder
    public Violation(
            Copyright copyright,
            String violatorName,
            LocalDateTime violateDate,
            Integer violateMoment,
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

    public void updateReactLevel(Integer code) {
        ViolateReactLevel reactLevel = ViolateReactLevel.get(code);

        if(reactLevel == ViolateReactLevel.EXAMINE)
            this.reactLevel = ViolateReactLevel.EXAMINE;

        else if(reactLevel == ViolateReactLevel.REACT)
            this.reactLevel = ViolateReactLevel.REACT;

        else if(reactLevel == ViolateReactLevel.COMPLETED)
            this.reactLevel = ViolateReactLevel.COMPLETED;

        else
            return;
    }

    public void updatePaymentLink(String paymentLink) {
        if(StringUtils.isNullOrEmpty(paymentLink))
            return;

        this.agreementPaymentLink = paymentLink;
    }
}
