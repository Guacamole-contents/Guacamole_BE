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

    public String toString(){
        if(this.value.equals("EXAMINE")) return "저작권자 검토";
        else if(this.value.equals("REACT")) return "침해 대응중";
        else if(this.value.equals("COMPLETED")) return "대응 완료";

        else{
            throw new IllegalArgumentException("잘못된 접근입니다.");
        }
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
