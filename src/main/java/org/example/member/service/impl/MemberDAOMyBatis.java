package org.example.member.service.impl;

import org.example.member.service.AuthorityVO;
import org.example.member.service.MemberDAO;
import org.example.member.service.MemberVO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository("memberDaoMyBatis")
public class MemberDAOMyBatis implements MemberDAO {

    private static final Logger logger = LoggerFactory.getLogger(MemberDAOMyBatis.class);
    @Resource(name="memberMapper")
    private MemberMapper memberMapper;

    public MemberDAOMyBatis() {
        logger.info("===> MemberDAOMyBatis 생성");
    }

    @Override
    public int selectDuplicateId(MemberVO memberVo) throws Exception {
        logger.info("===> MyBatis로 selectCountMember() 기능 처리");
        return memberMapper.selectDuplicateId(memberVo);
    }

    @Override
    public MemberVO selectMemberInfo(String memberUuid) throws Exception {
        logger.info("===> MyBatis로 selectMemberInfo() 기능 처리");
        return memberMapper.selectMemberInfo(memberUuid);
    }

    @Override
    public int insertSingUpMember(MemberVO memberVo) throws Exception {
        logger.info("===> MyBatis로 insertSingUpMember() 기능 처리");
        return memberMapper.insertSingUpMember(memberVo);
    }

    @Override
    public int insetAuthoritySetting(AuthorityVO authorityVO) throws Exception {
        logger.info("===> MyBatis로 insetAuthoritySetting() 기능 처리");
        return memberMapper.insetAuthoritySetting(authorityVO);
    }

    @Override
    public MemberVO findByUsername(String memberId) throws Exception {
        logger.info("===> MyBatis로 findByUsername() 기능 처리");
        return memberMapper.findByUsername(memberId);
    }

    @Override
    public List<String> findAuthoritiesByUsername(String memberId) throws Exception {
        logger.info("===> MyBatis로 findAuthoritiesByUsername() 기능 처리");
        return memberMapper.findAuthoritiesByUsername(memberId);
    }
}