package project.guakamole.domain.applicant.searchtype;

import project.guakamole.domain.copyright.searchtype.CopyrightSearchType;

public enum ApplicantSearchType {

    USER_ID("USER_ID"), //1 : userId로 검색
    CHANEL_NAME("CHANEL_NAME"), //2 : 채널명으로 검색
    EMAIL("EMAIL") //3 : 이메일로 검색
    ;

    private final String value;

    ApplicantSearchType(String value) {
        this.value = value;
    }

    public static ApplicantSearchType get(Integer num){
        if(num == null)
            return null;

        else if(num == 1) return ApplicantSearchType.USER_ID;
        else if(num == 2) return ApplicantSearchType.CHANEL_NAME;
        else if(num == 3) return ApplicantSearchType.EMAIL;

        throw new IllegalArgumentException("잘못된 접근입니다.");
    }
}
