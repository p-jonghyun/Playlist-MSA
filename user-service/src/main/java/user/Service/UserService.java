package user.Service;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import user.DTO.UserDto;
import user.Entity.User;
import user.Exception.CUserNotFoundException;
import user.Exception.UserAlreadyExistsException;
import user.Repository.UserRepository;

import static org.springframework.security.oauth2.common.OAuth2AccessToken.SCOPE;
import static org.springframework.security.oauth2.common.util.OAuth2Utils.CLIENT_ID;

@Service
@AllArgsConstructor
public class UserService {

    private UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

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
}
