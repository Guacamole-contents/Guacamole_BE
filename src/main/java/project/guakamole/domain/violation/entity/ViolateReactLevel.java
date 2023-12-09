package project.guakamole.domain.violation.entity;

import lombok.Getter;

@Getter
public enum ViolateReactLevel {
    /*
    검토단계, 대응단계, 완료단계
     */
    EXAMINE("EXAMINE"), REACT("REACT"), COMPLETED("COMPLETED");

    private final String value;

    ViolateReactLevel(String value){
        this.value = value;
    }

    public static ViolateReactLevel get(Integer code){
        if(code == 1)
            return ViolateReactLevel.EXAMINE;
        else if(code == 2)
            return ViolateReactLevel.REACT;
        else if(code == 3)
             return ViolateReactLevel.COMPLETED;

        throw new IllegalArgumentException("잘못된 접근입니다.");
    }
}
