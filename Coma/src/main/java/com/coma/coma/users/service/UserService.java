package com.coma.coma.users.service;

import com.coma.coma.sms.SmsController;
import com.coma.coma.users.domain.Users;
import com.coma.coma.users.dto.UserDto;
import com.coma.coma.users.dto.UserResponseDto;
import com.coma.coma.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private SmsController smsController;

    @Autowired
    public void setSmsController(@Lazy SmsController smsController) {
        this.smsController = smsController;
    }


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

    // 사용자 정보 업데이트
    public void updateUser(String oldId, String newId, String name, String phoneNumber, String password) {
        Users user = userRepository.findByUserIdName(oldId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + oldId));

        if (!oldId.equals(newId) && checkDuplicateId(newId)) {
            throw new IllegalArgumentException("이미 사용 중인 아이디입니다.");
        }

        user.setId(newId);
        user.setName(name);
        user.setPhoneNumber(phoneNumber);

        if (password != null && !password.isEmpty()) {
            user.setPassword(passwordEncoder.encode(password));
        }

        userRepository.update(user);
    }


    public void deleteUser(String id) {
        Optional<Users> user = userRepository.findByUserIdName(id);
        if (user.isPresent()) {
            userRepository.delete(user.get());
        } else {
            throw new IllegalArgumentException("User not found with id: " + id);
        }
    }

    public boolean checkDuplicateId(String id) {
        return userRepository.existsById(id);
    }

    public void sendVerificationCode(String phoneNumber) {
        smsController.sendOne(Map.of("phoneNumber", phoneNumber));
    }

    // 사용자 존재 여부 확인 메서드(비밀번호 찾기)
    public boolean checkUserExists(String id, String phoneNumber) {
        return userRepository.existsByIdAndPhoneNumber(id, phoneNumber);
    }

    // 비밀번호 재설정 메서드
    public void resetPassword(String id, String phoneNumber, String newPassword) {
        if (!checkUserExists(id, phoneNumber)) {
            throw new IllegalArgumentException("해당 아이디와 전화번호를 가진 사용자가 존재하지 않습니다.");
        }

        Users user = userRepository.findByUserIdName(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + id));

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.update(user);
    }

}
