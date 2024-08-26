package com.coma.coma.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        String username = null;
        String jwt = null;

        // 쿠키에서 JWT 토큰 추출
        if (request.getCookies() != null) {
            Cookie jwtCookie = Arrays.stream(request.getCookies())
                    .filter(cookie -> "jwtToken".equals(cookie.getName()))
                    .findFirst()
                    .orElse(null);

            if (jwtCookie != null) {
                jwt = jwtCookie.getValue();
                try {
                    username = jwtUtil.extractUsername(jwt);
                } catch (Exception e) {
                    // JWT 토큰이 유효하지 않거나 만료된 경우 예외 처리
                    handleInvalidJwt(response);
                    return;
                }
            }
        }

        // Authorization 헤더에서 JWT 토큰 추출 (필요시 사용)
        /*final String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            try {
                username = jwtUtil.extractUsername(jwt);
            } catch (Exception e) {
                handleInvalidJwt(response);
                return;
            }
        }*/

        // jwt 검증 후 인증객체 생성 및 설정 (JWT 토큰을 통해 스프링 시큐리티가 인증을 처리할 수 있도록 연결)
        // jwt 토큰을 통해 인증된 사용자는 추가적인 로그인 절차 없이도 스프링 시큐리티에서 인증된 사용자로 취급
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            if (jwtUtil.validateToken(jwt, userDetails.getUsername())) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            } else {
                // JWT가 유효하지 않은 경우
                handleInvalidJwt(response);
                return;
            }
        }

        chain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        // 로그인 요청은 필터링하지 않음
        return request.getServletPath().equals("/api/users/login");
    }

    private void handleInvalidJwt(HttpServletResponse response) throws IOException {
        // 쿠키 삭제
        Cookie cookie = new Cookie("jwtToken", null);
        cookie.setPath("/");
        cookie.setMaxAge(0); // 쿠키 즉시 만료
        response.addCookie(cookie);

        // 로그인 페이지로 리다이렉트
        response.sendRedirect("/users/login");
    }
}
