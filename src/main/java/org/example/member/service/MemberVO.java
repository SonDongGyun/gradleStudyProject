package org.example.member.service;

import org.example.member.status.MemberStatus;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class MemberVO {

    private String memberUuid;
    private String memberId;
    private String memberPw;
    private String memberName;
    private String memberPhone;
    private String memberEmail;
    private String memberOtherMatters;
    private MemberStatus memberStatus;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date registryDate;

    public String getMemberUuid() {
        return memberUuid;
    }

    public void setMemberUuid(String memberUuid) {
        this.memberUuid = memberUuid;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getMemberPw() {
        return memberPw;
    }

    public void setMemberPw(String memberPw) {
        this.memberPw = memberPw;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getMemberPhone() {
        return memberPhone;
    }

    public void setMemberPhone(String memberPhone) {
        this.memberPhone = memberPhone;
    }

    public String getMemberEmail() {
        return memberEmail;
    }

    public void setMemberEmail(String memberEmail) {
        this.memberEmail = memberEmail;
    }

    public String getMemberOtherMatters() {
        return memberOtherMatters;
    }

    public void setMemberOtherMatters(String memberOtherMatters) {
        this.memberOtherMatters = memberOtherMatters;
    }

    public MemberStatus getMemberStatus() {
        return memberStatus;
    }

    public void setMemberStatus(MemberStatus memberStatus) {
        this.memberStatus = memberStatus;
    }

    public Date getRegistryDate() {
        return registryDate;
    }

    public void setRegistryDate(Date registryDate) {
        this.registryDate = registryDate;
    }

    @Override
    public String toString() {
        return "MemberVO { "
                + "memberUuid=\'" + memberUuid + "\'"
                + ", memberId=\'" + memberId + "\'"
                + ", memberPw=\'" + memberPw + "\'"
                + ", memberName=\'" + memberName + "\'"
                + ", memberPhone=\'" + memberPhone + "\'"
                + ", memberEmail=\'" + memberEmail + "\'"
                + ", memberOtherMatters=\'" + memberOtherMatters + "\'"
                + ", memberStatus=\'" + memberStatus + "\'"
                + ", registryDate=\'" + registryDate + "\'"
                + " }";
    }
}