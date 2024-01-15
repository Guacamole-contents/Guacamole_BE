package project.guakamole.domain.violation.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import project.guakamole.domain.violation.entity.ViolationContractFile;

@AllArgsConstructor
@Getter
public class ContractInfoDto {
    private String contractFileName;
    private String contractUrl;
    private long  contractFileId;

    public static ContractInfoDto of(ViolationContractFile contractFile)
    {
        return new ContractInfoDto(
                contractFile.getOriginalFileName(),
                contractFile.getUrl(),
                contractFile.getId());
    }
}
