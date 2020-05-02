package notification.DTO;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class UserDTO {
    @Getter
    public static class RabbitUsers {
        private List<Long> users = new ArrayList<>();
        private String title;
        private String msg;
        private String criteria;

    }
}
