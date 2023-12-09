package project.guakamole.domain.violation.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateViolationRequest {
    private String paymentLink;
    private Integer reactLevel;
    // 1: 저작권자 검토, 2: 침해 대응중, 3: 대응 완료
}
