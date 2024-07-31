package org.example.member.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.member.service.AuthorityVO;
import org.example.member.service.MemberService;
import org.example.member.service.MemberVO;
import org.example.member.status.MemberAuthority;
import org.example.member.status.MemberStatus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/member")
public class MemberController {

    @Resource(name="memberService")
    private MemberService memberService;

    @Autowired
    @Qualifier("authenticationManager")
    private AuthenticationManager authenticationManager;

    @RequestMapping(value="/logIn.do", method = RequestMethod.GET)
    public String showLoginForm() {
        return "member/memberLogIn";
    }

    @ResponseBody
    @RequestMapping(value="/authenticationProcess.do", method = RequestMethod.POST)
    public String authenticate(HttpServletRequest request, RedirectAttributes redirectAttributes) {

        String memberId = request.getParameter("memberId");
        String memberPw = request.getParameter("memberPw");

        try {

            // 사용자 인증을 위한 UsernamePasswordAuthenticationToken 객체 생성
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(memberId, memberPw);

            // AuthenticationManager를 사용하여 인증 수행
            Authentication authentication = authenticationManager.authenticate(token);

            // 인증 성공 후 SecurityContext에 인증 객체 설정
            SecurityContextHolder.getContext().setAuthentication(authentication);

            return "redirect:/main.do";

        } catch (Exception e) {
            redirectAttributes.addAttribute("error", true);
            return "redirect://logIn.do?logout";
        }
    }

    @RequestMapping(value="/main.do", method = RequestMethod.GET)
    public String firstWelcomePage() {
        return "member/memberMain";
    }

    @RequestMapping(value="/singUp.do", method = RequestMethod.GET)
    public String memberSingUp() {
        return "member/memberSingUp";
    }

    @RequestMapping(value="/memberRegistry.do", method = RequestMethod.POST)
    public void singUpProcessing(HttpServletRequest request, HttpServletResponse response) throws Exception {

        boolean move = true;
        String errorMessage = null;
        String memberUuid = null;

        // @step#01 회원가입
        MemberVO memberVo = new MemberVO();

        if(request.getParameter("memberId").isEmpty() == false) {
            memberVo.setMemberId(request.getParameter("memberId"));
        }

        if(request.getParameter("memberPw").isEmpty() == false) {

            // BCryptPasswordEncoder 생성
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

            String memberPw = request.getParameter("memberPw");

            // 비밀번호 암호화
            String encodedPassword = passwordEncoder.encode(memberPw);

            memberVo.setMemberPw(encodedPassword);
        }

        if(request.getParameter("memberName").isEmpty() == false) {
            memberVo.setMemberName(request.getParameter("memberName"));
        }

        if(request.getParameter("memberPhone").isEmpty() == false) {
            memberVo.setMemberPhone(request.getParameter("memberPhone"));
        }

        if(request.getParameter("memberEmail").isEmpty() == false) {
            memberVo.setMemberEmail(request.getParameter("memberEmail"));
        }
        if(request.getParameter("memberOtherMatters").isEmpty() == false) {
            memberVo.setMemberOtherMatters(request.getParameter("memberOtherMatters"));
        }

        memberUuid = String.valueOf(UUID.randomUUID());
        memberVo.setMemberUuid(memberUuid);

        memberVo.setMemberStatus(MemberStatus.ACTIVE);

        if(memberService.insertSingUpMember(memberVo) > 0) {
            move = true;
        } else {
            move = false;
            errorMessage = "회원가입에 실패하였습니다.\n다시 시도하여 주시기 바랍니다.";
        }

        // @step#02. 회원 권한 설정
        if(move == true) {

            AuthorityVO authorityVo = new AuthorityVO();
            authorityVo.setMemberUuid(memberUuid);
            authorityVo.setMemberAuthority(MemberAuthority.ROLE_USER);

            if(memberService.insetAuthoritySetting(authorityVo) > 0) {
                move = true;
            } else {
                move = false;
                errorMessage = "회원 권한 설정에 실패하였습니다.\n다시 시도하여 주시기 바랍니다.";
            }
        }

        // @step#03. 회원 가입 완료
        if(move == true) {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=utf-8");
            PrintWriter out = response.getWriter();
            out.println("<script type='text/javascript'>");
            out.println("alert('회원가입에 성공였습니다.\\n로그인하여 다시 사용해 주시기 바랍니다.');");
            out.println("window.location.replace('/logIn.do')");
            out.println("</script>");
            out.flush();
        } else {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=utf-8");
            PrintWriter out = response.getWriter();
            out.println("<script type='text/javascript'>");
            out.printf("alert(%s);", errorMessage);
            out.println("window.location.replace('/singUp.do');");
            out.println("</script>");
            out.flush();
        }
    }

    @RequestMapping(value="/duplicateId.do", method = RequestMethod.POST)
    public void duplicateId(HttpServletRequest request, HttpServletResponse response) throws Exception {

        // @step#01 아이디 중복 조회
        MemberVO memberVo = new MemberVO();

        if(request.getParameter("memberId").isEmpty() == false) {
            memberVo.setMemberId(request.getParameter("memberId"));
        }

        int resultCount = memberService.selectDuplicateId(memberVo);

        // @step#01 아이디 중복 조회 결과 반환
        Map<String, Object> responseBody = new HashMap<>();

        if(resultCount > 0) {
            responseBody.put("status", "failure");
            responseBody.put("message", request.getParameter( "memberId" ) + "는\n이미 사용중인 아이디 입니다.");
        } else {
            responseBody.put("status", "success");
            responseBody.put("message", "사용가능한 아이디 입니다.");
        }

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().write(new ObjectMapper().writeValueAsString(responseBody));
        response.getWriter().flush();
    }
}