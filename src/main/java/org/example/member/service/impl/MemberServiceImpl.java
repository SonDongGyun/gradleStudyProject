package org.example.member.service.impl;

import org.example.member.service.AuthorityVO;
import org.example.member.service.MemberDAO;
import org.example.member.service.MemberVO;
import org.example.member.service.MemberService;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("memberService")
public class MemberServiceImpl implements MemberService {

    @Resource(name="memberDaoMyBatis")
    private MemberDAO memberDAO;

    @Override
    public int selectDuplicateId(MemberVO memberVo) throws Exception {
        return memberDAO.selectDuplicateId(memberVo);
    }


    @Override
    public int insertSingUpMember(MemberVO memberVo) throws Exception {
        return memberDAO.insertSingUpMember(memberVo);
    }

    @Override
    public int insetAuthoritySetting(AuthorityVO authorityVO) throws Exception {
        return memberDAO.insetAuthoritySetting(authorityVO);
    }
}