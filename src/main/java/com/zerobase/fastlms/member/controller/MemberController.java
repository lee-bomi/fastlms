package com.zerobase.fastlms.member.controller;


import com.zerobase.fastlms.member.model.MemberInput;

import com.zerobase.fastlms.member.service.impl.MemberServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberServiceImpl memberService;

    @RequestMapping(value = "/member/register", method = RequestMethod.GET)
    public String register() {
        return "member/register";
    }
    @RequestMapping(value = "/member/register", method = RequestMethod.POST)
    public String registerSubmit(Model model, HttpServletRequest request, MemberInput parameter) {

        boolean result = memberService.register(parameter);

        model.addAttribute("result", result);

        return "member/register_complete";
    }

    @GetMapping("/member/email-auth?id={uuid}")
    public String success(@PathVariable UUID uuid) {

        //서비스계층에서 Member 인증여부 변경
        return "인증성공";
    }
}
