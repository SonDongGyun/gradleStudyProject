package org.example.member.service;

import java.util.List;

public interface MemberDAO {

    int selectDuplicateId(MemberVO vo) throws Exception;

    MemberVO selectMemberInfo(String memberUuid) throws Exception;

    int insertSingUpMember(MemberVO vo) throws Exception;

    int insetAuthoritySetting(AuthorityVO authorityVo) throws Exception;

    MemberVO findByUsername(String memberId) throws Exception;

    List<String> findAuthoritiesByUsername(String memberId) throws Exception;
}