package user.Service;

import lombok.AllArgsConstructor;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import user.DTO.UserDto;
import user.Entity.Genre;
import user.Entity.Locale;
import user.Entity.User;
import user.Exception.CUserNotFoundException;
import user.Exception.UserAlreadyExistsException;
import user.Repository.UserRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.security.oauth2.common.OAuth2AccessToken.SCOPE;
import static org.springframework.security.oauth2.common.util.OAuth2Utils.CLIENT_ID;
import static user.Entity.Genre.hiphop;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private RabbitTemplate rabbitTemplate;

    public boolean isExistedEmail(String email) {
        return userRepository.findByEmail(email) != null;
    }

    @Transactional
    public User createUser(UserDto.SignupReq dto) {
        if(isExistedEmail(dto.getEmail()))
            throw new UserAlreadyExistsException();
        return userRepository.save(dto.toEntity(passwordEncoder.encode(dto.getPassword())));
    }

    public User getUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(CUserNotFoundException::new);
        return user;
    }

    @RabbitListener(queues = "q.requestUsersWithNoti.locale")
    public void listenRabbitLocale(String m) {

        ObjectMapper objectMapper = new ObjectMapper();
        try {

            UserDto.RabbitData dto = objectMapper.readValue(m, UserDto.RabbitData.class);

            List<Locale> locales = new ArrayList<>();
            locales.add(Locale.valueOf(dto.getCriteria()));

            List<User> users = userRepository.findByLocalesInAndAlbumCreatedNotification(locales, true);
            UserDto.RabbitUsers data = new UserDto.RabbitUsers(users, dto.getTitle(), dto.getMsg(), dto.getCriteria());
            String json = objectMapper.writeValueAsString(data);

            rabbitTemplate.convertAndSend("q.returnUsersWithNoti.locale", json);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @RabbitListener(queues = "q.requestUsersWithNoti.genre")
    public void listenRabbitGenre(String m) {

        ObjectMapper objectMapper = new ObjectMapper();
        try {

            UserDto.RabbitData dto = objectMapper.readValue(m, UserDto.RabbitData.class);

            List<Genre> genres = new ArrayList<>();
            genres.add(Genre.valueOf(dto.getCriteria()));

            List<User> users = userRepository.findByGenresInAndSongCreatedNotification(genres, true);
            UserDto.RabbitUsers data = new UserDto.RabbitUsers(users, dto.getTitle(), dto.getMsg(), dto.getCriteria());
            String json = objectMapper.writeValueAsString(data);

            rabbitTemplate.convertAndSend("q.returnUsersWithNoti.genre", json);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
