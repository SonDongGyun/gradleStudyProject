package org.example.security;

import org.example.member.service.MemberDAO;
import org.example.member.service.MemberVO;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

public class SecurityUserDetailsService implements UserDetailsService {

    @Resource(name="memberDaoMyBatis")
    private MemberDAO memberDAO;

    // loadUserByUsername(String memberId)는 사용자 정보를 조회하고 `UserDetails`를 반환한다.
    @Override
    public UserDetails loadUserByUsername(String memberId) throws UsernameNotFoundException {
        try {

            // 입력받은 사용자 ID로 사용자 UUID를 조회
            MemberVO memberVO = memberDAO.findByUsername(memberId);

            if(memberVO == null) {
                throw new UsernameNotFoundException("User not found with username: " + memberId);
            }

            // 조회한 사용자 UUID로 인증 수행
            List<String> authorities = memberDAO.findAuthoritiesByUsername(memberVO.getMemberUuid());

            List<GrantedAuthority> grantedAuthorities = authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
            // getMemberUuid() : 사용자 UUID
            // getMemberPw() : 사용자의 비밀번호
            // grantedAuthorities : 사용자의 권한 목록
            return new User(memberVO.getMemberUuid(), memberVO.getMemberPw(), grantedAuthorities);

        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }
}