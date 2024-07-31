package org.example.member.service.impl;

import org.apache.ibatis.annotations.Mapper;
import org.example.member.service.AuthorityVO;
import org.example.member.service.MemberVO;

import java.util.List;

@Mapper
public interface MemberMapper {

    int selectDuplicateId(MemberVO vo) throws Exception;

    MemberVO selectMemberInfo(String memberUuid);

    int insertSingUpMember(MemberVO vo) throws Exception;

    int insetAuthoritySetting(AuthorityVO authorityVO) throws Exception;

    MemberVO findByUsername(String memberId);

    List<String> findAuthoritiesByUsername(String memberId);
}