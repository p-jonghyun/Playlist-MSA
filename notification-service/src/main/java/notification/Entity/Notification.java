package notification.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Notification {

    @Id @GeneratedValue
    private Long id;
    private Long userId;

    private String title;
    private String message;
    private boolean checked;

    @Enumerated(EnumType.STRING)
    private NotificationType notificationType;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
