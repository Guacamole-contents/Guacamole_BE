package project.guakamole.domain.common.file;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.IOUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class FileUtil {

    private static final String FILE_EXTENSION_SEPARATOR = ".";
    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String[] saveFile(MultipartFile multipartFile) throws RuntimeException {
        String[] fileNames = uploadFileToS3(multipartFile);
        return fileNames;
    }

    public List<String[]> saveFiles(List<MultipartFile> multipartFiles) {
        return multipartFiles.stream()
                .filter(multipartFile -> !multipartFile.isEmpty())
                .map(this::saveFile)
                .toList();
    }

    public void deleteFile(String fileUrl){
        try {
            // S3에서 삭제
            amazonS3.deleteObject(new DeleteObjectRequest(bucket, fileUrl));
            log.info(String.format("[%s] deletion complete", fileUrl));
        } catch (AmazonServiceException e) {
            e.printStackTrace();
        } catch (SdkClientException e) {
            e.printStackTrace();
        }
    }

    public DownloadFileDto download(String fileUrl) throws IOException {
        // 객체 다운  fileUrl : 파일네임.파일확장자
        S3Object s3Object = amazonS3.getObject(new GetObjectRequest(bucket, fileUrl));
        S3ObjectInputStream objectInputStream = s3Object.getObjectContent();
        byte[] bytes = IOUtils.toByteArray(objectInputStream);

        MediaType mediaType = contentType(fileUrl);
        Long contentLength = Long.valueOf(bytes.length);
        String[] arr = fileUrl.split("/"); //폴더명이 있을시
        String type = arr[arr.length - 1]; // 파일명
        String fileName = URLEncoder.encode(type, "UTF-8").replaceAll("\\+", "%20");

        return new DownloadFileDto(bytes, mediaType, contentLength, fileName);
    }

    private MediaType contentType(String keyname) {
        String[] arr = keyname.split("\\.");
        String type = arr[arr.length - 1];
        switch (type) {
            case "txt":
                return MediaType.TEXT_PLAIN;
            case "png":
                return MediaType.IMAGE_PNG;
            case "jpg":
                return MediaType.IMAGE_JPEG;
            case "pdf":
                return MediaType.APPLICATION_PDF;
            default:
                return MediaType.APPLICATION_OCTET_STREAM;
        }
    }


    private String[] uploadFileToS3(MultipartFile multipartFile) {
        try {
            String originalFilename = multipartFile.getOriginalFilename();
            String translatedFileName = translateFileName(originalFilename);

            PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, translatedFileName, multipartFile.getInputStream(), new ObjectMetadata())
                    .withCannedAcl(CannedAccessControlList.PublicRead);
            amazonS3.putObject(putObjectRequest);

            return new String[]{originalFilename, translatedFileName};
        } catch (IOException e) {
            throw new RuntimeException("File translation failed", e);
        }
    }

    private String translateFileName(String originalFilename) {
        String ext = extractExt(originalFilename);
        String uuid = UUID.randomUUID().toString();
        return uuid + FILE_EXTENSION_SEPARATOR + ext;
    }

    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(FILE_EXTENSION_SEPARATOR);
        return originalFilename.substring(pos + 1);
    }
}
