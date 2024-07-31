package org.example.member.service;

import org.example.member.status.MemberAuthority;

public class AuthorityVO {

    private int seq;
    private String memberUuid;
    private MemberAuthority memberAuthority;

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public String getMemberUuid() {
        return memberUuid;
    }

    public void setMemberUuid(String memberUuid) {
        this.memberUuid = memberUuid;
    }

    public MemberAuthority getMemberAuthority() {
        return memberAuthority;
    }

    public void setMemberAuthority(MemberAuthority memberAuthority) {
        this.memberAuthority = memberAuthority;
    }

    @Override
    public String toString() {
        return "AuthorityVO { "
                + "seq=\'" + seq + "\'"
                + ", memberUuid=\'" + memberUuid + "\'"
                + ", memberAuthority=\'" + memberAuthority + "\'"
                + " }";
    }
}