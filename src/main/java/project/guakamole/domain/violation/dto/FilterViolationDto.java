package project.guakamole.domain.violation.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FilterViolationDto {

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    private List<Integer> agreementTypes;
    // 1: 보류, 2: 침해합의, 3: 삭제요청, 4:법적대응
    //null -> 모두 포함
    private List<Integer> reactLevels;
    // 1: 저작권자 검토, 2: 침해 대응중, 3: 대응 완료
    // null -> 모두 포함

    private Long minAgreementAmount;
    private Long maxAgreementAmount;
    //최소금액만 입력시 최대금액 무제한
    //최대금액만 입력시 최소금액 0

    public FilterViolationDto(LocalDateTime startDate, LocalDateTime endDate, List<Integer> agreementTypes, List<Integer> reactLevels, Long minAgreementAmount, Long maxAgreementAmount) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.agreementTypes = agreementTypes;
        this.reactLevels = reactLevels;
        this.minAgreementAmount = minAgreementAmount;
        this.maxAgreementAmount = maxAgreementAmount;
    }
}
