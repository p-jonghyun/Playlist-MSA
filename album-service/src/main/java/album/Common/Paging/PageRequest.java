package album.Common.Paging;

import lombok.Getter;
import org.springframework.data.domain.Sort;

import static org.springframework.data.domain.Sort.Direction.DESC;

@Getter
public class PageRequest {

    private int page;
    private int size = 10;
    private Sort.Direction direction = DESC;

    public void setPage(int page) {
        this.page = page <= 0 ? 1 : page;
    }

    public org.springframework.data.domain.PageRequest of(String category) {
        return org.springframework.data.domain.PageRequest.of(page - 1, size, direction, category);
    }

}