package notification.Service;

import lombok.RequiredArgsConstructor;
import notification.Entity.Notification;
import notification.Repository.NotificationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

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



}
