package org.example.api.controller;

import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiImplicitParam;
import org.example.member.service.MemberService;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/access")
@Api(tags = "01. 사용자 접속 RestAPI 서비스")
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


    @RequestMapping(value="/authenticationProcess.do", method=RequestMethod.POST)
    @ApiOperation(value="회원 인증 처리", notes="회원 인증을 수행합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name="memberId", value="회원 아이디", required=true, dataTypeClass=String.class, paramType="query"),
            @ApiImplicitParam(name="memberPw", value="회원 비밀번호", required=true, dataTypeClass=String.class, paramType="query")
    })
    public void authenticate(@RequestParam String memberId, @RequestParam String memberPw, @ApiIgnore RedirectAttributes redirectAttributes) {
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


    @RequestMapping(value="/jwtTokenValidation.do", method=RequestMethod.POST)
    @ApiOperation(value="JWT 유효성 검사", notes="JWT 토큰 사용가능 유무 검사")
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