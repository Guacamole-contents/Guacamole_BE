package project.guakamole.domain.common.file;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.MediaType;

@AllArgsConstructor
@Getter
public class DownloadFileDto {
    private final byte[] bytes;
    private final MediaType mediaType;
    private final Long contentLength;
    private final String fileName;
}
