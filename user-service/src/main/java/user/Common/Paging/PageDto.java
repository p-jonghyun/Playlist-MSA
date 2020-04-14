package user.Common.Paging;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import org.springframework.data.domain.Page;
import user.DTO.UserDto;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PageDto {

    static String URL = "https://SERVER_URL/v1/user?page=";

    private String url = "https://localhost:8090/";
    private String first;
    private String prev;
    private String last;
    private String next;

    public PageDto(Page<?> page, String url) {

        this.url += (url + "?page=");

        this.first = this.url + "1";
        this.last = this.url + (page.getTotalPages() + 1);

        if (page.getPageable().getPageNumber() == 0) this.prev = null;
        else this.prev = this.url + (page.getPageable().getPageNumber() + 0);

        if(this.first.equals(this.last)) this.next = null;
        else this.next = this.url + (page.getPageable().getPageNumber() + 2);

    }
}

