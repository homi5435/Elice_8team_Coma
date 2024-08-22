package com.coma.coma.users.controller;

import com.coma.coma.security.JwtUtil;
import com.coma.coma.users.dto.*;
import com.coma.coma.users.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;

    // 사용자 정보 조회
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUser(@PathVariable String id) {
        UserResponseDto user = userService.getUserById(id);

        if (user != null) {
            return ResponseEntity.ok(user);  // 200 OK와 함께 사용자 정보를 반환
        } else {
            return ResponseEntity.notFound().build();  // 404 Not Found를 반환
        }
    }

    // 회원가입
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserDto userDto) {
        try {
            userService.registerUser(userDto);  // 회원가입 로직 실행
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequestDto.getId(), loginRequestDto.getPassword())
            );
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequestDto.getId());
        final String jwt = jwtUtil.generateToken(userDetails.getUsername());

        Cookie cookie = new Cookie("jwtToken", jwt);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(24 * 60 * 60); // 1일

        response.addCookie(cookie);

        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }



    // 로그아웃


    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("jwtToken", null);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(0); // 즉시 만료
        response.addCookie(cookie);

        return ResponseEntity.ok().build();
    }


    // 아이디 중복 체크 API
    @GetMapping("/check-id/{id}")
    public ResponseEntity<Boolean> checkDuplicateId(@PathVariable String id) {
        boolean isDuplicate = userService.checkDuplicateId(id);
        return ResponseEntity.ok(isDuplicate);
    }

}
