package user.Controller;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import user.Entity.User;
import user.Repository.UserRepository;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public User join() {


        User newUser = User.builder()
                .password(passwordEncoder.encode("0000"))
                .email("jongjong1994@gmail.com")
                .name("jonghyun")
                .phoneNumber("010-7148-4933")
                .userType(0)
                .build();

        return userRepository.save(newUser);
    }
}
