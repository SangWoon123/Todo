package com.gdsc.todo.global.details;

import com.gdsc.todo.user.dao.User;
import com.gdsc.todo.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username)
                .orElseThrow(()->new RuntimeException("존재하지않는 유저입니다."));

        return new CustomUser(user.getUsername(),user.getPassword(),user.getEmail(),user.getRole());
    }
}
