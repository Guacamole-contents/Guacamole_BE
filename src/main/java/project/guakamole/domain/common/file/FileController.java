package project.guakamole.domain.common.file;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@RequiredArgsConstructor
@RequestMapping("/file")
public class FileController {
    private final FileUtil fileUtil;

    @GetMapping("/download")
    public ResponseEntity<byte[]> download(
            @RequestParam("fileUrl") String fileUrl
    ) throws IOException {
        DownloadFileDto dto = fileUtil.download(fileUrl);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(dto.getMediaType()); //파일 url 명시
        httpHeaders.setContentLength(dto.getContentLength()); // 파일 크기 명시
        httpHeaders.setContentDispositionFormData("attachment", dto.getFileName()); // 파일이름 지정

        return new ResponseEntity<>(dto.getBytes(), httpHeaders, HttpStatus.OK);
    }
}
