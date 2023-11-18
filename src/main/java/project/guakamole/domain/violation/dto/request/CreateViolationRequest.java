package project.guakamole.domain.violation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateViolationRequest {
    @NotNull(message = "저작권 ID는 필수 데이터입니다.")
    private Long sourceId;
    @NotBlank(message = "침해자명은 필수 데이터입니다.")
    private String violatorName;
    @NotNull(message = "침해 날짜는 필수 데이터입니다.")
    private LocalDateTime violateDate;
    @NotNull(message = "침해 구간은 필수 데이터입니다.")
    private Integer violateMoment;
    @NotBlank(message = "침해 링크는 필수 데이터입니다.")
    private String link;
    private List<String> imageUrls;
}
