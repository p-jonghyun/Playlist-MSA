package notification.Service;

import lombok.RequiredArgsConstructor;
import notification.DTO.UserDTO;
import notification.Entity.Notification;
import notification.Entity.NotificationType;
import notification.Repository.NotificationRepository;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final RabbitTemplate rabbitTemplate;

    @Transactional(readOnly = true)
    public List<Notification> getNewNotifications(Long userId) {

        List<Notification> notifications = notificationRepository.findByUserIdAndCheckedOrderByCreatedAtDesc(userId, false);
        return notifications;
    }

    @Transactional(readOnly =  true)
    public List<Notification> getOldNotifications(Long userId) {
        List<Notification> notifications = notificationRepository.findByUserIdAndCheckedOrderByCreatedAtDesc(userId, true);
        return notifications;
    }

    public void markAsRead(List<Notification> notifications) {
        notifications.forEach(n -> n.setChecked(true));
        notificationRepository.saveAll(notifications);
    }

    public void deleteNotifications(Long userId) {
        notificationRepository.deleteByUserIdAndChecked(userId, true);
    }

    @RabbitListener(queues = "q.returnUsersWithNoti.locale")
    public void listenRabbitLocale(String val) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            UserDTO.RabbitUsers data = objectMapper.readValue(val, UserDTO.RabbitUsers.class);
            List<Long> users = data.getUsers();

            for(Long id : users) {
                Notification notification = Notification.builder()
                        .title(data.getTitle())
                        .message(data.getMsg())
                        .userId(id)
                        .notificationType(NotificationType.SUBSCRIBED_LOCAL_CREATED)
                        .build();
                notificationRepository.save(notification);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @RabbitListener(queues = "q.returnUsersWithNoti.genre")
    public void listenRabbitGenre(String val) {

        ObjectMapper objectMapper = new ObjectMapper();
        try {

            UserDTO.RabbitUsers data = objectMapper.readValue(val, UserDTO.RabbitUsers.class);
            List<Long> users = data.getUsers();

            for(Long id : users) {
                Notification notification = Notification.builder()
                        .title(data.getTitle())
                        .message(data.getMsg())
                        .userId(id)
                        .notificationType(NotificationType.SUBSCRIBED_GENRE_CREATED)
                        .build();
                notificationRepository.save(notification);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
