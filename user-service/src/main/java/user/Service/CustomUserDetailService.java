package user.Service;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import user.Entity.User;
import user.Repository.UserRepository;

@Service("CustomUserDetailService")
@AllArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private UserRepository userRepository;
    private final PasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(email);

        if(user == null) throw new UsernameNotFoundException("없어용");
        return user;
        //return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),user.getAuthorities());
    }


}
