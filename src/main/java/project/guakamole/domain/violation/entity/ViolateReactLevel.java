package project.guakamole.domain.violation.entity;

import lombok.Getter;

@Getter
public enum ViolateReactLevel {
    /*
    검토단계, 대응단계, 완료단계
     */
    EXAMINE("EXAMINE"), REACT("REACT"), COMPLETED("COMPLETED");

    private final String value;

    ViolateReactLevel(String value) {
        this.value = value;
    }
}
