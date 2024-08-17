package ai.platform.proma.security;

import ai.platform.proma.exception.ApiException;
import ai.platform.proma.exception.ErrorDefine;
import ai.platform.proma.repositroy.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        Collection<SimpleGrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority("ROLE_USER"));

        UserLoginForm user = userRepository.findBySocialIdAndRefreshToken(username)
                .orElseThrow(() -> new ApiException(ErrorDefine.LOGIN_ACCESS_DENIED));

        return CustomUserDetail.create(user);
    }

    public CustomUserDetail loadUserByUsernameAndUserRole(String username, String role) {
        Collection<SimpleGrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority("ROLE_" + role));

        UserLoginForm user;
        user = userRepository.findBySocialIdAndRefreshToken(username)
                .orElseThrow(() -> new ApiException(ErrorDefine.LOGIN_ACCESS_DENIED));

        return CustomUserDetail.create(user);
    }
}
