package project.guakamole.domain.common.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SearchCondDto {
    private Integer searchType;
    private String keyword;

    public SearchCondDto(Integer searchType, String keyword) {
        this.searchType = searchType;
        this.keyword = keyword;
    }
}
