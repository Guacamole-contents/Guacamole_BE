package project.guakamole.domain.violation.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import project.guakamole.domain.violation.entity.Violation;
import project.guakamole.domain.violation.entity.ViolationContractFile;
import project.guakamole.domain.violation.entity.ViolationImage;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class DetailViolationResponse {
    private final Long sourceId;
    private final Long violateId;
    private final String violatorName;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private final LocalDateTime violateDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private final LocalDateTime reportDate;
    private final String violateMoment;
    private final String agreementType;
    private final Long agreementAmount;
    private final String reactLevel;
    private final String agreementPaymentLink;
    private final List<String> contractFileNames;
    private final List<String> contractUrls;
    private final List<String> imageFileNames;
    private final List<String> imageUrls;

    @Builder
    public DetailViolationResponse(
            Long sourceId,
            Long violateId,
            String violatorName,
            LocalDateTime violateDate,
            LocalDateTime reportDate,
            String violateMoment,
            String agreementType,
            Long agreementAmount,
            String reactLevel,
            String agreementPaymentLink,
            List<String> contractFileNames,
            List<String> contractUrls,
            List<String> imageFileNames,
            List<String> imageUrls) {
        this.sourceId = sourceId;
        this.violateId = violateId;
        this.violatorName = violatorName;
        this.violateDate = violateDate;
        this.reportDate = reportDate;
        this.violateMoment = violateMoment;
        this.agreementType = agreementType;
        this.agreementAmount = agreementAmount;
        this.reactLevel = reactLevel;
        this.agreementPaymentLink = agreementPaymentLink;
        this.contractFileNames = contractFileNames;
        this.contractUrls = contractUrls;
        this.imageFileNames = imageFileNames;
        this.imageUrls = imageUrls;
    }

    public static DetailViolationResponse of(Violation violation){
        List<String> contractUrls = null;
        List<String> contractFileNames = null;
        List<String> imagetUrls = null;
        List<String> imageFileNames = null;
        if(violation.getContractFiles() != null && !violation.getContractFiles().isEmpty()){
            contractUrls = violation.getContractFiles().stream().map(ViolationContractFile::getUrl).collect(Collectors.toList());
            contractFileNames = violation.getContractFiles().stream().map(ViolationContractFile::getOriginalFileName).collect(Collectors.toList());
        }

        if(violation.getImages() != null && !violation.getImages().isEmpty()){
            imagetUrls = violation.getImages().stream().map(ViolationImage::getUrl).collect(Collectors.toList());
            imageFileNames = violation.getImages().stream().map(ViolationImage::getOriginalFileName).collect(Collectors.toList());
        }

        return DetailViolationResponse.builder()
                .sourceId(violation.getCopyright().getId())
                .violateId(violation.getId())
                .violatorName(violation.getViolatorName())
                .violateDate(violation.getViolateDate())
                .reportDate(violation.getCreatedDate())
                .violateMoment(violation.getViolateMoment())
                .agreementType(violation.getAgreementType().getValue())
                .agreementAmount(violation.getAgreementAmount())
                .reactLevel(violation.getReactLevel().getValue())
                .agreementPaymentLink(violation.getAgreementPaymentLink())
                .contractFileNames(contractFileNames)
                .contractUrls(contractUrls)
                .imageFileNames(imageFileNames)
                .imageUrls(imagetUrls)
                .build()
                ;
    }
}
