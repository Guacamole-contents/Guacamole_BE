package project.guakamole.domain.violation.dto.response;


import lombok.Builder;
import lombok.Getter;
import project.guakamole.domain.violation.entity.Violation;

import java.time.format.DateTimeFormatter;

@Getter
public class FindViolationResponse {
    private final String reportDate;
    private final String agreementType;
    private final Long agreementAmount;
    private final String reactLevel;
    private final Long sourceId;
    private final String violatorName;

    @Builder
    private FindViolationResponse(String reportDate, String agreementType, Long agreementAmount, String reactLevel, Long sourceId, String violatorName) {
        this.reportDate = reportDate;
        this.agreementType = agreementType;
        this.agreementAmount = agreementAmount;
        this.reactLevel = reactLevel;
        this.sourceId = sourceId;
        this.violatorName = violatorName;
    }

    public static FindViolationResponse of(Violation violation){
        return FindViolationResponse.builder()
                .reportDate(violation.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .agreementType("법적 대응") //임시
                .agreementAmount(Long.valueOf(999999)) //임시
                .reactLevel(violation.getReactLevel().getValue())
                .sourceId(violation.getCopyright().getId())
                .violatorName(violation.getViolatorName())
                .build()
                ;
    }
}
