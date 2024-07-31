package org.example.member.service;

public interface MemberService {

    int selectDuplicateId(MemberVO memberVo) throws Exception;

    int insertSingUpMember(MemberVO memberVo) throws Exception;

    int insetAuthoritySetting(AuthorityVO authorityVo) throws Exception;
}