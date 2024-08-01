package org.example.api.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiImplicitParam;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.member.service.MemberVO;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/swagger")
@Api(tags = "02. Swagger RestAPI 서비스 샘플")
public class SwaggerController {

    private final Map<String, Object> responseBody = new HashMap<>();

    @RequestMapping(value="/swaggerGetOne.do", method=RequestMethod.GET)
    @ApiOperation(value="GET 테스트#01", notes="Swagger API GET 방식 사용 테스트 01 - JWT 토큰 유효성 검사", hidden=false)
    public void getSwaggerOne(HttpServletResponse response) throws Exception {

        responseBody.put("status", "success");
        responseBody.put("message", "토큰이 유효");

        response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);  // 206
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().write(new ObjectMapper().writeValueAsString(responseBody));
        response.getWriter().flush();
    }

    @RequestMapping(value="/swaggerGetTwo/{koName}/{enName}", method=RequestMethod.GET)
    @ApiOperation(value="GET 테스트#02", notes="Swagger API GET 방식 사용 테스트 02 - GET 방식 Request 확인", hidden=false)
    @ApiImplicitParams({
            @ApiImplicitParam(name="koName", value="한글 이름", required=true, dataTypeClass=String.class, paramType="path"),
            @ApiImplicitParam(name="enName", value="영문 이름", required=true, dataTypeClass=String.class, paramType="path")
    })
    public void getSwaggerTwo(@PathVariable("koName") String koName, @PathVariable("enName") String enName, HttpServletResponse response) throws Exception {

        String message = "";

        // 한글 이름과 영어 이름 둘다 존재하는 경우
        if(StringUtils.isEmpty("koName") == false && StringUtils.isEmpty("enName") == false) {

            message = String.format("한글 이름 : %s, 영문 이름 : %s", koName, enName);

            responseBody.put("status", "success");
            responseBody.put("message", message);
            response.setStatus(HttpServletResponse.SC_OK);  // 200
        }

        // 한글 이름과 영어 이름 중 하나라도 입력되지 않은 경우
        else {

            message = "사용자 ID, 사용자 이름이 입력되지 않았습니다.";

            responseBody.put("status", "failure");
            responseBody.put("message", message);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400
        }

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().write(new ObjectMapper().writeValueAsString(responseBody));
        response.getWriter().flush();
    }

    @RequestMapping(value="/swaggerPost.do", method=RequestMethod.POST)
    @ApiOperation(value="POST 테스트", notes="Swagger API POST 방식 사용 테스트", hidden=false)
    public void postSwagger(@RequestBody MemberVO memberVO, HttpServletResponse response) throws Exception {

        responseBody.put("status", "success");
        responseBody.put("memberId", memberVO.getMemberId());
        responseBody.put("memberName", memberVO.getMemberName());
        responseBody.put("memberPhone", memberVO.getMemberPhone());
        responseBody.put("memberEmail", memberVO.getMemberEmail());

        response.setStatus(HttpServletResponse.SC_OK);  // 200
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().write(new ObjectMapper().writeValueAsString(responseBody));
        response.getWriter().flush();
    }

    @RequestMapping(value="/swaggerPut/{memberId}", method=RequestMethod.PUT)
    @ApiOperation(value="PUT 테스트", notes="Swagger API PUT 방식 사용 테스트", hidden=false)
    public void swaggerPut(@RequestBody MemberVO memberVO, HttpServletResponse response) throws Exception {


        if(memberVO.getMemberId().isEmpty() == false && memberVO.getMemberName().isEmpty() == false && memberVO.getMemberPhone().isEmpty() == false && memberVO.getMemberEmail().isEmpty() == false) {
            responseBody.put("status", "success");
            responseBody.put("message", "회원 가입에 성공하였습니다.");
            responseBody.put("memberId", memberVO.getMemberId());
            responseBody.put("memberName", memberVO.getMemberName());
            responseBody.put("memberPhone", memberVO.getMemberPhone());
            responseBody.put("memberEmail", memberVO.getMemberEmail());
            response.setStatus(HttpServletResponse.SC_CREATED); // 201
        } else {
            responseBody.put("status", "success");
            responseBody.put("message", "누락된 항목의 값이 존재합니다.");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400
        }

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().write(new ObjectMapper().writeValueAsString(responseBody));
        response.getWriter().flush();
    }


    @RequestMapping(value="/swaggerDelete/{memberId}", method=RequestMethod.DELETE)
    @ApiOperation(value="DELETE 테스트", notes="Swagger API DELETE 방식 사용 테스트", hidden=false)
    @ApiImplicitParam(name="memberId", value="사용자 아이디", required=true, dataTypeClass=String.class, paramType="path")
    public void swaggerDelete(@PathVariable("memberId") String memberId, HttpServletResponse response) throws Exception {

        String message = String.format("%s 아이디가 삭제되었습니다.", memberId);

        responseBody.put("status", "success");
        responseBody.put("message", message);

        response.setStatus(HttpServletResponse.SC_NO_CONTENT); // 204
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().write(new ObjectMapper().writeValueAsString(responseBody));
        response.getWriter().flush();
    }
}