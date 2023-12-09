package project.guakamole.domain.creator.searchType;

import project.guakamole.domain.copyright.searchtype.CopyrightSearchType;

public enum CreatorSearchType {
    CREATOR_ID("CREATOR_ID"), //1 : CREATOR_ID 로 검색
    CREATOR_NAME("CREATOR_NAME"), //2 : 저작자명 명으로 검색
    EMAIL("EMAIL"); //3 : 이메일로 검색

    private final String value;

    CreatorSearchType(String value) {
        this.value = value;
    }

    public static CreatorSearchType get(Integer num){
        if(num == null)
            return null;

        else if(num == 1) return CreatorSearchType.CREATOR_ID;
        else if(num == 2) return CreatorSearchType.CREATOR_NAME;
        else if(num == 3) return CreatorSearchType.EMAIL;

        throw new IllegalArgumentException("잘못된 접근입니다.");
    }
}
