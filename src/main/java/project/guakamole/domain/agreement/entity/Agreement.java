package project.guakamole.domain.agreement.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.guakamole.domain.violation.entity.Violation;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class Agreement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "violation_id", nullable = false)
    private Violation violation;

    @Column(name = "agreement_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private AgreementType agreementType;

    @Column(name = "agreement_amount", nullable = false)
    private Long agreementAmount;

    @Column(name = "contract")
    private String contract;


}
