package project.guakamole.domain.applicant.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class ReviewApplicantRequest {
    @NotNull(message = "보류/승인/거절에 대한 결과는 필수 데이터입니다.")
    private final Integer result;
    //0 : 보류, 1 : 승인, 2 : 거절
    @NotNull(message = "null 값은 불가능합니다.")
    private final String note;

    public ReviewApplicantRequest(Integer result, String note) {
        this.result = result;
        this.note = note;
    }
}
