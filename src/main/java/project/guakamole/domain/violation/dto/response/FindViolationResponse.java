package project.guakamole.domain.violation.dto.response;


import lombok.Builder;
import lombok.Getter;
import project.guakamole.domain.violation.entity.AgreementType;
import project.guakamole.domain.violation.entity.Violation;

import java.time.LocalDateTime;

@Getter
public class FindViolationResponse {
    private final LocalDateTime reportDate;
    private final String agreementType;
    private final Long agreementAmount;
    private final String reactLevel;
    private final Long sourceId;
    private final String ownerName;

    @Builder
    private FindViolationResponse(
            LocalDateTime reportDate,
            String agreementType,
            Long agreementAmount,
            String reactLevel,
            Long sourceId,
            String ownerName)
    {
        this.reportDate = reportDate;
        this.agreementType = agreementType;
        this.agreementAmount = agreementAmount;
        this.reactLevel = reactLevel;
        this.sourceId = sourceId;
        this.ownerName = ownerName;
    }

    public static FindViolationResponse of(Violation violation){
        return FindViolationResponse.builder()
                .reportDate(violation.getCreatedDate())
                .agreementType(AgreementType.HOLD.getValue()) //임시
                .agreementAmount(Long.valueOf(0)) //임시
                .reactLevel(violation.getReactLevel().getValue())
                .sourceId(violation.getCopyright().getId())
                .ownerName(violation.getCopyright().getOwnerName())
                .build()
                ;
    }
}
