package com.coma.coma.users.controller;

import com.coma.coma.security.CustomUserDetails;
import com.coma.coma.users.dto.UserResponseDto;
import com.coma.coma.users.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("users/login")
    public String loginPage() {
        return "user/login"; // user/login.html 템플릿을 반환
    }

    @GetMapping("users/register")
    public String registerPage() {
        return "user/register"; // user/register.html 템플릿을 반환
    }

    @GetMapping("users/{id}")
    public String getUserInfo(@PathVariable("id") String id, Model model) {
        // 사용자 정보를 서비스에서 조회
        UserResponseDto user = userService.getUserById(id);

        // 가져온 데이터를 모델에 추가하여 뷰에 전달
        model.addAttribute("user", user);

        return "user/userInfo";  // userinfo.html 템플릿을 반환
    }

    @GetMapping("/userInfo")
    public String redirectToUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = null;

        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            userId = userDetails.getUsername();  // username이 userId로 설정된 경우
            //System.out.println(userDetails.getUsername());
            //System.out.println(userDetails.getName());
            //System.out.println(userDetails.getGroupId());
        }

        if (userId != null) {
            return "redirect:/users/" + userId;  // userId로 리디렉션
        } else {
            // 만약 userId가 null이면 로그인 페이지로 리디렉션
            return "redirect:/users/login";
        }
    }


    @GetMapping("/users/edit/{id}")
    public String editUserInfo(@PathVariable("id") String id, Model model) {
        UserResponseDto user = userService.getUserById(id);
        model.addAttribute("user", user);

        return "user/editUserInfo";  // editUserInfo.html 템플릿을 반환
    }

    @PostMapping("/users/update/{id}")
    public String updateUserInfo(@PathVariable("id") String oldId,
                                 @RequestParam("id") String newId,
                                 @RequestParam("name") String name,
                                 @RequestParam("phoneNumber") String phoneNumber,
                                 @RequestParam("password") String password,
                                 Model model) {
        try {
            userService.updateUser(oldId, newId, name, phoneNumber, password);
            return "redirect:/users/" + newId;
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("user", userService.getUserById(oldId));
            return "user/editUserInfo";
        }
    }

    @PostMapping("/users/delete/{id}")
    public String deleteUserInfo(@PathVariable("id") String id) {
        userService.deleteUser(id);
        return "redirect:/api/boards";
    }
}
