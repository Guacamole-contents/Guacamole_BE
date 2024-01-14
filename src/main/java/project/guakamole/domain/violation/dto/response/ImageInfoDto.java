package project.guakamole.domain.violation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import project.guakamole.domain.violation.entity.ViolationImage;

@Getter
@AllArgsConstructor
public class ImageInfoDto {
    private String imageFileName;
    private String imageFileUrl;
    private long imageFileId;

    public static ImageInfoDto of(ViolationImage imageFile)
    {
        return new ImageInfoDto(
                imageFile.getOriginalFileName(),
                imageFile.getUrl(),
                imageFile.getId());
    }
}
