package project.guakamole.domain.creator.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCreatorActiveRequest {
    @NotNull
    private Integer active;
    //1: 정상 이용, 2: 사용 정지
}
