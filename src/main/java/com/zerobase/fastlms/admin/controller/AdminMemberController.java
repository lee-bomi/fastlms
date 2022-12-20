package com.zerobase.fastlms.admin.controller;

import com.zerobase.fastlms.admin.dto.MemberDto;
import com.zerobase.fastlms.admin.model.MemberParam;
import com.zerobase.fastlms.admin.model.MemberStatusInput;
import com.zerobase.fastlms.member.entity.Member;
import com.zerobase.fastlms.member.service.MemberService;
import com.zerobase.fastlms.member.service.impl.MemberServiceImpl;
import com.zerobase.fastlms.util.PageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminMemberController {

    private final MemberService memberService;

    @GetMapping("/admin/member/list.do")
    public String list(Model model, MemberParam parameter) {

        parameter.init();

        List<MemberDto> members = memberService.list(parameter);
        model.addAttribute("list", members);

        long totalCount = 0;
        if (members != null && members.size() > 0) {
            totalCount = members.get(0).getTotalCount();
        }
        String queryString = parameter.getQueryString();
        PageUtil pageUtil = new PageUtil(totalCount, parameter.getPageSize(), 0, queryString);
        model.addAttribute("pager", pageUtil.pager());
        model.addAttribute("totalCount", totalCount);


        return "admin/member/list";
    }

    @GetMapping("/admin/member/detail.do")
    public String detail(Model model, MemberParam parameter) {

        parameter.init();

        MemberDto member = memberService.detail(parameter.getUserId());
        model.addAttribute("member", member);

        return "admin/member/detail";
    }

    @PostMapping("/admin/member/status.do")
    public String status(Model model, MemberStatusInput parameter) {

        boolean result = memberService.updateStatus(parameter.getUserId(), parameter.getUserStatus());
        return "redirect:/admin/member/detail.do?userId=" + parameter.getUserId();
    }
}
