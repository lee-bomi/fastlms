package com.zerobase.fastlms.member.service.impl;

import com.zerobase.fastlms.component.MailComponent;
import com.zerobase.fastlms.member.entity.Member;
import com.zerobase.fastlms.member.model.MemberInput;
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
                .phone(parameter.getUserName())
                .password(encPassword)
                .regDt(LocalDateTime.now())
                .emailAuthYn(false)
                .emailAuthKey(uuid)
                .build();

        memberRepository.save(member);

        String email = parameter.getUserId();
        String subject = "fastLms 사이트 가입을 축하드려요";
        String text = "<p> 가입 축하드립니다. </p> <p>아래 링크 클릭하셔 가입을 완료하세요</p>"
                + "<div><a href ='http://localhost:8080/member/email-auth?id=" + uuid + "'>가입 완료</a></div>";
        mailComponent.sendMail(email, subject, text);

        return true;
    }

    @Override
    public boolean emailAuth(String uuid) {

        //해당uuid를 가진 유저를 찾는다
        Optional<Member> authMember = memberRepository.findByEmailAuthKey(uuid);
        if (!authMember.isPresent()) {
            return false;
        }
        Member member = authMember.get();
        member.setEmailAuthYn(true);
        member.setEmailAuthDt(LocalDateTime.now());
        memberRepository.save(member);

        return true;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<Member> optionalMember = memberRepository.findById(username);
        if (!optionalMember.isPresent()) {
            throw new UsernameNotFoundException("회원 정보가 존재하지 않습니다");
        }

        Member member = optionalMember.get();
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));


        return new User(member.getUserId(), member.getPassword(), grantedAuthorities);
    }
}
