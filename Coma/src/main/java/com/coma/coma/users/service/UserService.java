package com.coma.coma.users.service;

import com.coma.coma.users.domain.Users;
import com.coma.coma.users.dto.UserDto;
import com.coma.coma.users.dto.UserResponseDto;
import com.coma.coma.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public UserResponseDto getUserByUserId(int user_id) {
        return userRepository.getUserByUserId(user_id);
    }

    public UserResponseDto getUserById(String id) {
        Users user = userRepository.findByUserIdName(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + id));
        return new UserResponseDto(user.getUserId(), user.getId(), user.getName(), user.getPhoneNumber(), user.getPassword(), user.getSignupDate());
    }

    public void registerUser(UserDto userDto) {
        // 아이디 중복 체크
        if (userRepository.existsById(userDto.getId())) {
            throw new IllegalArgumentException("이미 사용 중인 아이디입니다.");
        }

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(userDto.getPassword());
        userDto.setPassword(encodedPassword);

        // UserDto를 Users 엔티티로 변환
        Users user = new Users();
        user.setGroupId(2); // 기본 그룹 ID 설정 (예: 2 - 일반유저)
        user.setId(userDto.getId());
        user.setPassword(userDto.getPassword());
        user.setName(userDto.getName());
        user.setPhoneNumber(userDto.getPhoneNumber());

        // DB에 사용자 저장
        userRepository.save(user);
    }

    public boolean checkDuplicateId(String id) {
        return userRepository.existsById(id);
    }
}
