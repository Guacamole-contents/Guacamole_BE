package project.guakamole.domain.copyright.searchtype;

import lombok.Getter;

@Getter
public enum CopyrightSearchType {
    SOURCE_ID("SOURCE_ID"), //1 : sourceId로 검색
    COPYRIGHT_NAME("COPYRIGHT_NAME"),//2 : 저작물 명으로 검색
    OWNER_NAME("OWNER_NAME"); //3 : 저작권자 명으로 검색

    private final String value;

    CopyrightSearchType(String value) {
        this.value = value;
    }

    public static CopyrightSearchType get(Integer num){
        if(num == null)
            return null;

        else if(num == 1) return CopyrightSearchType.SOURCE_ID;
        else if(num == 2) return CopyrightSearchType.COPYRIGHT_NAME;
        else if(num == 3) return CopyrightSearchType.OWNER_NAME;

        throw new IllegalArgumentException("잘못된 접근입니다.");
    }
}
