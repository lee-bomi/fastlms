package com.zerobase.fastlms.member.entity;

public interface MemberCode {
    /**
     * 회원 가입 요청중
     */
    String MEMBER_STATUS_REQ = "REQ";
    /**
     * 이용중인 상태
     */
    String MEMBER_STATUS_ING = "ING";
    /**
     * 이용 정지 상태
     */
    String MEMBER_STATUS_STOP = "STOP";
}
