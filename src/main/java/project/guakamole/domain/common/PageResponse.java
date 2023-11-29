package project.guakamole.domain.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Page;

@Getter
@AllArgsConstructor
public class PageResponse<T> {

    private T data;
    private long totalElements;
    private int totalPages;
    private boolean hasNext;

    public static PageResponse of(Object data, Page page){
        return new PageResponse(
                data,
                page.getTotalElements(),
                page.getTotalPages(),
                page.hasNext());
    }
}
