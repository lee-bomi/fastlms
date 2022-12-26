package com.zerobase.fastlms.main;

import com.zerobase.fastlms.component.MailComponent;
import com.zerobase.fastlms.log.service.LogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final MailComponent mailComponent;

    @GetMapping("/")
    public String index(HttpServletRequest request) {

//        mailComponent.sendMail(email, subject, text);
        return "/index";
    }

    @GetMapping("/error/denied")
    public String errorDenied() {

//        mailComponent.sendMail(email, subject, text);
        return "/error/denied";
    }
}
