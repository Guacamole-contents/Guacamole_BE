package project.guakamole.domain.agreement.entity;

public enum AgreementType {
    HOLD("HOLD"), AGREEMENT("AGREEMENT"), REQUEST_DELETE("REQUEST_DELETE"), LEGAL_RESPONSE("LEGAL_RESPONSE");

    private final String value;

    AgreementType(String value) {
        this.value = value;
    }
}
