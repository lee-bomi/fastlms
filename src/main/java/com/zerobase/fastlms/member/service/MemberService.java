package com.zerobase.fastlms.member.service;

import com.zerobase.fastlms.member.entity.Member;
import com.zerobase.fastlms.member.model.MemberInput;
import com.zerobase.fastlms.member.model.ResetPasswordInput;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface MemberService extends UserDetailsService {

    boolean register(MemberInput parameter);

    /**
     * uuid에 해당하는 계정을 활성화함
     * @param uuid
     * @return
     */
    boolean emailAuth(String uuid);

    /**
     * 입력한 이메일로 비밀번호 초기화 정보를 전송
     * @param parameter
     * @return
     */
    public boolean sendResetPassword(ResetPasswordInput parameter);

    /**
     * 입력받은 uuid에 대해서 password로 초기화 함
     */
    public boolean resetPassword(String uuid, String password);

    /**
     * 입력받은 uuid값이 유효한지 확인
     */
    public boolean checkResetPassword(String uuid);

    /**
     * admin에서 list에 넣을 회원정보를 가져온다
     */
    public List<Member> list();
}
