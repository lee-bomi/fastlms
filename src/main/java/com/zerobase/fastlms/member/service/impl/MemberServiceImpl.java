package com.zerobase.fastlms.member.service.impl;

import com.zerobase.fastlms.admin.dto.MemberDto;
import com.zerobase.fastlms.admin.mapper.MemberMapper;
import com.zerobase.fastlms.admin.model.MemberParam;
import com.zerobase.fastlms.component.MailComponent;
import com.zerobase.fastlms.member.entity.Member;
import com.zerobase.fastlms.member.exception.MemberNotEmailAuthException;
import com.zerobase.fastlms.member.exception.MemberStopUserEmailAuthException;
import com.zerobase.fastlms.member.model.MemberInput;
import com.zerobase.fastlms.member.model.ResetPasswordInput;
import com.zerobase.fastlms.member.repository.MemberRepository;
import com.zerobase.fastlms.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final MailComponent mailComponent;
    private final MemberMapper memberMapper;
    @Override
    public boolean register(MemberInput parameter) {

        Optional<Member> optionalMember = memberRepository.findById(parameter.getUserId());
        if (optionalMember.isPresent()) {
            return false;
        }
        String encPassword = BCrypt.hashpw(parameter.getPassword(), BCrypt.gensalt());
        String uuid = UUID.randomUUID().toString();

        Member member = Member.builder()
                .userId(parameter.getUserId())
                .userName(parameter.getUserName())
                .phone(parameter.getPhone())
                .password(encPassword)
                .regDt(LocalDateTime.now())
                .emailAuthYn(false)
                .emailAuthKey(uuid)
                .userStatus(Member.MEMBER_STATUS_REQ)
                .build();

        memberRepository.save(member);

        String email = parameter.getUserId();
        String subject = "fastLms ????????? ????????? ???????????????";
        String text = "<p> ?????? ??????????????????. </p> <p>?????? ?????? ???????????? ????????? ???????????????</p>"
                + "<div><a href ='http://localhost:8080/member/email-auth?id=" + uuid + "'>?????? ??????</a></div>";
        mailComponent.sendMail(email, subject, text);

        return true;
    }

    @Override
    public boolean emailAuth(String uuid) {

        //??????uuid??? ?????? ????????? ?????????
        Optional<Member> authMember = memberRepository.findByEmailAuthKey(uuid);
        if (!authMember.isPresent()) {
            return false;
        }
        Member member = authMember.get();

        //?????? ?????? ???????????????????????? ????????????????????? ??????????????????????????? ????????????????????????
        if (member.isEmailAuthYn()) {
            return false;
        }

        member.setUserStatus(Member.MEMBER_STATUS_ING);
        member.setEmailAuthYn(true);
        member.setEmailAuthDt(LocalDateTime.now());
        memberRepository.save(member);

        return true;
    }

    @Override
    public boolean sendResetPassword(ResetPasswordInput parameter) {

        Optional<Member> optionalMember = memberRepository.findByUserIdAndUserName(parameter.getUserId(), parameter.getUserName());
        if (!optionalMember.isPresent()) {
            throw new UsernameNotFoundException("?????? ????????? ???????????? ????????????");
        }

        Member member = optionalMember.get();
        String uuid = UUID.randomUUID().toString();

        member.setResetPasswordKey(uuid);
        member.setResetPasswordLimitDt(LocalDateTime.now().plusDays(1));
        memberRepository.save(member);

        String email = parameter.getUserId();
        String subject = "[fastLms] ???????????? ????????? ?????? ?????????";
        String text = "<p> fastlms ???????????? ????????? ?????? ????????? </p> <p>?????? ?????? ???????????? ????????? ????????? ????????????</p>"
                + "<div><a href ='http://localhost:8080/member/reset/password?id=" + uuid + "'>???????????? ?????????</a></div>";
        mailComponent.sendMail(email, subject, text);

        return true;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<Member> optionalMember = memberRepository.findById(username);
        if (!optionalMember.isPresent()) {
            throw new UsernameNotFoundException("?????? ????????? ???????????? ????????????");
        }

        Member member = optionalMember.get();

        if (Member.MEMBER_STATUS_REQ.equals(member.getUserStatus())) {
            throw new MemberNotEmailAuthException("???????????? ????????? ????????? ?????????????????????");
        }
        if (Member.MEMBER_STATUS_STOP.equals(member.getUserStatus())) {
            throw new MemberStopUserEmailAuthException("????????? ?????? ?????????");
        }
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        if (member.isAdminYn()) {
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }


        return new User(member.getUserId(), member.getPassword(), grantedAuthorities);
    }

    @Override
    public boolean resetPassword(String uuid, String password) {
        Optional<Member> optionalMember = memberRepository.findByResetPasswordKey(uuid);
        if (!optionalMember.isPresent()) {
            throw new UsernameNotFoundException("?????? ????????? ???????????? ????????????");
        }

        Member member = optionalMember.get();

        if (member.getResetPasswordLimitDt() == null) {
            throw new RuntimeException("????????? ????????? ????????????. ");
        }

        if (member.getResetPasswordLimitDt().isBefore(LocalDateTime.now())){
            throw new RuntimeException("????????? ????????? ????????????. ");
        }

        //?????????????????? ???????????? ??????
        String encPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        member.setPassword(encPassword);
        member.setResetPasswordKey("");
        member.setResetPasswordLimitDt(null);
        memberRepository.save(member);

        return true;
    }

    @Override
    public boolean checkResetPassword(String uuid) {
        Optional<Member> optionalMember = memberRepository.findByResetPasswordKey(uuid);
        if (!optionalMember.isPresent()) {
            return false;
        }

        //?????? ???????????? ????????? ??? ????????? ???????????? ????????????
        Member member = optionalMember.get();

        if (member.getResetPasswordLimitDt() == null) {
            throw new RuntimeException("????????? ????????? ????????????. ");
        }

        if (member.getResetPasswordLimitDt().isBefore(LocalDateTime.now())){
            throw new RuntimeException("????????? ????????? ????????????. ");
        }

        return true;
    }

    @Override
    public List<MemberDto> list(MemberParam param) {
        long totalCount = memberMapper.selectListCount(param);
        List<MemberDto> list = memberMapper.selectList(param);

        if (!CollectionUtils.isEmpty(list)) {
            int i = 0;
            for (MemberDto x : list) {
                x.setTotalCount(totalCount);
                x.setSeq(totalCount - param.getPageStart() - i);
                i++;
            }
        }
        return list;
//        List<Member> members = memberRepository.findAll();
    }

    @Override
    public MemberDto detail(String userId) {

        Optional<Member> optionalMember = memberRepository.findById(userId);
        if (!optionalMember.isPresent()) {
            return null;
        }
        Member member = optionalMember.get();
        return MemberDto.of(member);
    }

    @Override
    public boolean updateStatus(String userId, String userStatus) {
        Optional<Member> optionalMember = memberRepository.findById(userId);
        if (!optionalMember.isPresent()) {
            throw new UsernameNotFoundException("?????? ????????? ???????????? ????????????");
        }

        Member member = optionalMember.get();
        member.setUserStatus(userStatus);
        memberRepository.save(member);
        return true;
    }

    @Override
    public boolean updatePassword(String userId, String password) {
        Optional<Member> optionalMember = memberRepository.findById(userId);
        if (!optionalMember.isPresent()) {
            throw new UsernameNotFoundException("?????? ????????? ???????????? ????????????");
        }

        Member member = optionalMember.get();

        String encPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        member.setPassword(encPassword);
        memberRepository.save(member);
        return true;
    }
}
