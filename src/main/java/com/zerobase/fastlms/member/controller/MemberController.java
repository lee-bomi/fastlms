package com.zerobase.fastlms.member.controller;


import com.zerobase.fastlms.member.model.MemberInput;

import com.zerobase.fastlms.member.service.impl.MemberServiceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


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

    @GetMapping("/member/email-auth")
    public String emailAuth(Model model, HttpServletRequest request) {
        String uuid = request.getParameter("id");
        System.out.println("id : " + uuid);
        boolean result = memberService.emailAuth(uuid);
        model.addAttribute("result", result);

        return "member/email-auth";
    }

    @GetMapping("/member/info")
    public String memberInfo() {

        return "member/info";
    }
}
