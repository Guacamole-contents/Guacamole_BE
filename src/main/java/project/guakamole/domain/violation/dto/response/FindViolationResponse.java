package project.guakamole.domain.violation.dto.response;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import project.guakamole.domain.violation.entity.AgreementType;
import project.guakamole.domain.violation.entity.Violation;

import java.time.LocalDateTime;

@Getter
public class FindViolationResponse {
    private final Long violateId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private final LocalDateTime reportDate;
    private final String agreementType;
    private final Long agreementAmount;
    private final String reactLevel;
    private final Long sourceId;
    private final String ownerName;

    @Builder
    public FindViolationResponse(
            Long violateId,
            LocalDateTime reportDate,
            String agreementType,
            Long agreementAmount,
            String reactLevel,
            Long sourceId,
            String ownerName)
    {
        this.violateId = violateId;
        this.reportDate = reportDate;
        this.agreementType = agreementType;
        this.agreementAmount = agreementAmount;
        this.reactLevel = reactLevel;
        this.sourceId = sourceId;
        this.ownerName = ownerName;
    }

    public static FindViolationResponse of(Violation violation){
        return FindViolationResponse.builder()
                .violateId(violation.getId())
                .reportDate(violation.getCreatedDate())
                .agreementType(AgreementType.HOLD.toString()) //임시
                .agreementAmount(Long.valueOf(0)) //임시
                .reactLevel(violation.getReactLevel().toString())
                .sourceId(violation.getCopyright().getId())
                .ownerName(violation.getCopyright().getOwnerName())
                .build()
                ;
    }
}
