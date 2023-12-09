package project.guakamole.domain.creator.entity;

import lombok.Getter;
import project.guakamole.domain.applicant.entity.ApplicantApproveStatus;
@Getter
public enum CreatorActiveStatus {
    // 1 : ON ,  2 : OFF
    ON("ON"), OFF("OFF")
    ;

    private String value;

    CreatorActiveStatus(String value) {
        this.value = value;
    }

    public static CreatorActiveStatus get(Integer code){
        if(code == null)
            return null;

        if(code==1) return CreatorActiveStatus.ON;
        if(code==2) return CreatorActiveStatus.OFF;

        throw new IllegalArgumentException("잘못된 접근입니다.");
    }

}
