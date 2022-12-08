package com.zerobase.fastlms.member;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MemberController {

    @RequestMapping(value = "/member/register", method = RequestMethod.GET)
    public String register() {
        return "member/register";
    }
    @RequestMapping(value = "/member/register", method = RequestMethod.POST)
    public String registerSubmit(HttpServletRequest request, HttpServletResponse response, MemberInput memberInput) {

        System.out.println(memberInput.toString());

        return "member/register";
    }
}
