package project.guakamole.domain.violation.dto.response;

import lombok.Builder;
import lombok.Getter;
import project.guakamole.domain.violation.entity.Violation;

import java.time.LocalDateTime;

@Getter
public class DetailViolationResponse {
    private final Long sourceId;
    private final Long violateId;
    private final String violatorName;
    private final LocalDateTime violateDate;
    private final LocalDateTime reportDate;
    private final Integer violateMoment;
    private final String agreementType;
    private final Long agreementAmount;
    private final String reactLevel;
    private final String agreementPaymentLink;
    private final String contractUrl;

    @Builder
    public DetailViolationResponse(Long sourceId, Long violateId, String violatorName, LocalDateTime violateDate, LocalDateTime reportDate, Integer violateMoment, String agreementType, Long agreementAmount, String reactLevel, String agreementPaymentLink, String contractUrl) {
        this.sourceId = sourceId;
        this.violateId = violateId;
        this.violatorName = violatorName;
        this.violateDate = violateDate;
        this.reportDate = reportDate;
        this.violateMoment = violateMoment;
        this.agreementType = agreementType;
        this.agreementAmount = agreementAmount;
        this.reactLevel = reactLevel;
        this.agreementPaymentLink = agreementPaymentLink;
        this.contractUrl = contractUrl;
    }

    public static DetailViolationResponse of(Violation violation){
        return DetailViolationResponse.builder()
                .sourceId(violation.getCopyright().getId())
                .violateId(violation.getId())
                .violatorName(violation.getViolatorName())
                .violateDate(violation.getViolateDate())
                .reportDate(violation.getCreatedDate())
                .violateMoment(violation.getViolateMoment())
                .agreementType(violation.getAgreementType().getValue())
                .agreementAmount(violation.getAgreementAmount())
                .reactLevel(violation.getReactLevel().getValue())
                .agreementPaymentLink("www.payment.com")
                .contractUrl(violation.getContractUrl())
                .build()
                ;

    }
}
