package org.example.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
public class JwtLogoutSuccessHandler implements LogoutSuccessHandler {

    private JwtTokenProvider jwtTokenProvider;

    public void setJwtTokenProvider(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        Map<String, Object> responseBody = new HashMap<>();

        // 로그아웃 성공 시 JWT 토큰 파기 로직 수행
        String jwtToken = jwtTokenProvider.extractTokenFromRequest(request);
        jwtTokenProvider.invalidateToken(jwtToken);

        if(jwtTokenProvider.isTokenBlacklisted(jwtToken) == true) {

            // JWT 토큰 사용이 만료 or 파기됨
            responseBody.put("status", "success");
            responseBody.put("message", "사용자 로그아웃");
        } else {
            responseBody.put("status", "failure");
            responseBody.put("message", "로그아웃 실패");
        }

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().write(new ObjectMapper().writeValueAsString(responseBody));
        response.getWriter().flush();
    }
}