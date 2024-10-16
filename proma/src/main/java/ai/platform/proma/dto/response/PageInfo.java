package ai.platform.proma.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Page;

@Getter
@AllArgsConstructor
public class PageInfo {
    private int currentPage;
    private int totalPages;
    private int pageSize;
    private long totalItems;
    private int currentItems;

    public PageInfo(Page<?> page) {
        this.currentPage = page.getNumber();
        this.totalPages = page.getTotalPages();
        this.pageSize = page.getSize();
        this.totalItems = page.getTotalElements();
        this.currentItems = page.getNumberOfElements();
    }
}