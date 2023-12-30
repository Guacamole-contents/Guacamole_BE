package project.guakamole.domain.violation.searchtype;

import lombok.Getter;
@Getter
public enum ViolationSearchType {

    SOURCE_ID("SOURCE_ID"), //1 : sourceId로 검색
    OWNER_NAME("OWNER_NAME"); //2 : 저작권자 명으로 검색

    private final String value;

    ViolationSearchType(String value) {
        this.value = value;
    }

    public static ViolationSearchType get(Integer num){
        if(num == null)
            return null;

        else if(num == 1) return ViolationSearchType.SOURCE_ID;
        else if(num == 2) return ViolationSearchType.OWNER_NAME;

        throw new IllegalArgumentException("잘못된 접근입니다.");
    }
}
