package project.guakamole.domain.violation.entity;

import lombok.Getter;

@Getter
public enum AgreementType {
    //보류, 침해합의, 제거 요청, 법적대응
    HOLD("HOLD"), AGREEMENT("AGREEMENT"), REQUEST_DELETE("REQUEST_DELETE"), LEGAL_RESPONSE("LEGAL_RESPONSE");

    private final String value;

    AgreementType(String value) {
        this.value = value;
    }

    public String toString(){
        if(this.value.equals("HOLD")) return "보류";
        else if(this.value.equals("AGREEMENT")) return "침해 합의";
        else if(this.value.equals("REQUEST_DELETE")) return "제거 요청";
        else if(this.value.equals("LEGAL_RESPONSE")) return "법적 대응";

        else{
            throw new IllegalArgumentException("잘못된 접근입니다.");
        }
    }
}
