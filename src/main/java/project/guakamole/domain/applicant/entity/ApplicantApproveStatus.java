package project.guakamole.domain.applicant.entity;

import lombok.Getter;

@Getter
public enum ApplicantApproveStatus {
    HOLD("HOLD"), APPROVE("APPROVE"), DECLINE("DECLINE")
    ;

    private String value;

    ApplicantApproveStatus(String value) {
        this.value = value;
    }

    public static ApplicantApproveStatus get(Integer code){
        if(code == null)
            return null;

        if(code==1) return ApplicantApproveStatus.APPROVE;
        if(code==2) return ApplicantApproveStatus.DECLINE;

        throw new IllegalArgumentException("잘못된 접근입니다.");
    }
}
