package project.guakamole.domain.copyright;

import project.guakamole.domain.copyright.entity.Copyright;

public class CopyrightObjectCreator {
    private static final String ownerName = "저작권 소유자명";
    private static final String copyrightName = "저작권명";
    private static final String originalLink = "저작권 링크";

    public static Copyright copyright(Long id){
        return Copyright.builder()
                .copyrightName(copyrightName)
                .ownerName(ownerName)
                .originalLink(originalLink)
                .build();
    }

    public static Copyright copyright(String ownerName, String copyrightName, String originalLink){
        return Copyright.builder()
                .copyrightName(copyrightName)
                .ownerName(ownerName)
                .originalLink(originalLink)
                .build();
    }



}
