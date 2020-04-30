package notification.Common.Paging;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import org.springframework.data.domain.Page;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PageDto {

    private String current;
    private String first;
    private String prev;
    private String last;
    private String next;

    public PageDto(Page<?> page, String key) {

        String URL = "https://localhost:8090/";

        int currrentPage = page.getPageable().getPageNumber() + 1;
        int lastPage = page.getTotalPages() == 0 ? 1 : page.getTotalPages();

        URL += (key + "?page=");

        this.current = URL + currrentPage;
        this.first = URL + "1";
        this.last = URL + lastPage;

        if(lastPage == 1) {
            this.prev = null;
            this.next = null;
        }
        else {
            this.prev = currrentPage == 1 ? null : String.valueOf(currrentPage - 1);
            this.next = currrentPage == lastPage ? null : String.valueOf(currrentPage + 1);
        }

    }
}

