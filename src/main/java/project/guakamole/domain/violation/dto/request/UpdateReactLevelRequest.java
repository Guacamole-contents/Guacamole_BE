package project.guakamole.domain.violation.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateReactLevelRequest {

    @NotBlank(message = "변경할 대응 단계는 필수 데이터입니다.")
    private String reactLevel;
}
