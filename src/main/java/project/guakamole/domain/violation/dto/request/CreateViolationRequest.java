package project.guakamole.domain.violation.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateViolationRequest {
    private Long sourceId;
    private String violatorName;
    private LocalDateTime violateDate;
    private int violateMoment;
    private String link;
    private List<String> imageUrls;
}
