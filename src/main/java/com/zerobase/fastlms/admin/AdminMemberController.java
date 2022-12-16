package com.zerobase.fastlms.admin;

import com.zerobase.fastlms.member.entity.Member;
import com.zerobase.fastlms.member.service.MemberService;
import com.zerobase.fastlms.member.service.impl.MemberServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminMemberController {

    private final MemberService memberService;

    @GetMapping("/admin/member/list.do")
    public String list(Model model) {
        List<Member> list = memberService.list();
        model.addAttribute("list", list);
        return "admin/member/list";
    }
}
