package notification.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import notification.Entity.Notification;
import notification.Entity.NotificationType;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class NotificationDTO {

    @NoArgsConstructor
    @Getter
    public static class NotificationRes {

        private List<NotificationDetail> newGenreNotifications = new ArrayList<>();
        private List<NotificationDetail> newLocaleNotifications = new ArrayList<>();

        public NotificationRes(List<Notification> notifications) {

            for(Notification notification : notifications) {
                switch (notification.getNotificationType()) {
                    case SUBSCRIBED_GENRE_CREATED:
                        newGenreNotifications.add(new NotificationDetail(notification));
                        break;
                    case SUBSCRIBED_LOCAL_CREATED:
                        newLocaleNotifications.add(new NotificationDetail(notification));
                        break;
                }
            }
        }
    }

    @Getter
    @NoArgsConstructor
    public static class NotificationDetail {
        @Id
        @GeneratedValue
        private Long id;
        private Long userId;

        private String title;
        private String message;

        @CreatedDate
        @Column(name = "created_at")
        private LocalDateTime createdAt;

        public NotificationDetail(Notification notification) {
            this.userId = notification.getUserId();
            this.title = notification.getTitle();
            this.message = notification.getMessage();
            this.createdAt = notification.getCreatedAt();
        }
    }
}
