package com.coma.coma.security;

import com.coma.coma.users.domain.Users;
import com.coma.coma.users.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }



   /* @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        Users user = userRepository.findByUserIdName(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + id));

        return new org.springframework.security.core.userdetails.User(user.getId(), user.getPassword(), new ArrayList<>());
    }*/

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        Users user = userRepository.findByUserIdName(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + id));

        return new CustomUserDetails(
                user.getId(),
                user.getPassword(),
                user.getName(),       // 사용자 이름
                user.getGroupId(),    // 그룹 ID
                user.getUserId(),
                new ArrayList<>()
        );
    }
}
