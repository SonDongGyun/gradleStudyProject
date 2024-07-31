package org.example.api.controller;

import io.jsonwebtoken.Claims;
import org.example.member.service.MemberService;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/access")
public class AccessController {

    @Resource(name="memberService")
    private MemberService memberService;

    @Autowired
    @Qualifier("authenticationManager")
    private AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

    public AccessController(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @RequestMapping(value = "/authenticationProcess.do", method=RequestMethod.POST)
    public void authenticate(HttpServletRequest request, RedirectAttributes redirectAttributes) {

        String memberId = request.getParameter("memberId");
        String memberPw = request.getParameter("memberPw");

        try {

            // 사용자 인증을 위한 UsernamePasswordAuthenticationToken 객체 생성
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(memberId, memberPw);

            // AuthenticationManager를 사용하여 인증 수행
            Authentication authentication = authenticationManager.authenticate(token);

            // 인증 성공 후 SecurityContext에 인증 객체 설정
            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (Exception e) {
            redirectAttributes.addAttribute("error", true);
        }
    }

    @RequestMapping(value = "/disconnectionProcess.do", method=RequestMethod.POST)
    public void disconnect(HttpServletRequest request, HttpServletResponse response) throws Exception {

        Map<String, Object> responseBody = new HashMap<>();

        // 로그아웃 성공 시 JWT 토큰 파기 로직 수행
        String jwtToken = jwtTokenProvider.extractToken(request);
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


    @RequestMapping(value="/jwtTokenValidation.do", method = RequestMethod.POST)
    public void jwtTokenValidation(HttpServletRequest request, HttpServletResponse response) throws Exception {

        // 스프링 시큐리티로 사용자 ID 가져오기
        // Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 사용자ID 가져오기
        // String username = (String)authentication.getPrincipal();

        Map<String, Object> responseBody = new HashMap<>();

        String jwtToken = jwtTokenProvider.extractToken(request);

        // 토큰이 유효한 경우
        if(jwtTokenProvider.isTokenBlacklisted(jwtToken) == false) {

            // JWT 토큰 Claim 설정값 가져오기
            Claims claims = jwtTokenProvider.extractClaims(jwtToken);

            String memberUuid = claims.getSubject();
            String memberId = (String)claims.get("memberId");
            String memberName = (String)claims.get("memberName");


            responseBody.put("status", "success");
            responseBody.put("message", "토큰이 유효");
            responseBody.put("memberUuid", memberUuid);
            responseBody.put("memberId", memberId);
            responseBody.put("memberName", memberName);
        }

        // 토큰이 만료된 경우
        else {

            responseBody.put("status", "failure");
            responseBody.put("message", "토큰만료");
        }

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().write(new ObjectMapper().writeValueAsString(responseBody));
        response.getWriter().flush();
    }
}