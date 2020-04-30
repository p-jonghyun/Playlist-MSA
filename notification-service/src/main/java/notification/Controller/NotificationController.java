package notification.Controller;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import notification.Common.Result.Result;
import notification.Common.Result.ResultService;
import notification.DTO.NotificationDTO;
import notification.Entity.Notification;
import notification.Service.NotificationService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationService notificationService;
    private final ResultService resultService;

    @GetMapping("")
    @ResponseStatus(value = HttpStatus.OK)
    public Result getNewNotifications(HttpServletRequest request) {
        List<Notification> notifications = notificationService.getNewNotifications(getUserId(request));
        return resultService.getSuccessResult(new NotificationDTO.NotificationRes(notifications));
    }

    @GetMapping("/old")
    @ResponseStatus(value = HttpStatus.OK)
    public Result getOldNotifications(HttpServletRequest request) {
        List<Notification> notifications = notificationService.getOldNotifications(getUserId(request));
        return resultService.getSuccessResult(new NotificationDTO.NotificationRes(notifications));
    }

    @DeleteMapping("")
    @ResponseStatus(value = HttpStatus.OK)
    public Result deleteNotifications(HttpServletRequest request) {
        notificationService.deleteNotifications(getUserId(request));
        return resultService.getSuccessResult(null);
    }

    private Long getUserId(HttpServletRequest request) {

        String authorizationHeader = request.getHeader("Authorization");
        authorizationHeader = authorizationHeader.replace("Bearer ", "");

        Claims claims = Jwts.parser()
                .setSigningKey("jwt_secret_key".getBytes())
                .parseClaimsJws(authorizationHeader).getBody();

        Integer id = (Integer) claims.get("user_id");
        return Long.valueOf(id);
    }

}
