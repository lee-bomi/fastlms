package com.zerobase.fastlms;

import com.zerobase.fastlms.component.MailComponent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final MailComponent mailComponent;

    @GetMapping("/")
    public String index() {
        System.out.println("==============================");
        String email = "bomvll@naver.com";
        String subject = "메일왔어용";
        String text = "<p>안녕하세요 접니다</p>";
//        mailComponent.sendMail(email, subject, text);
        return "/index";
    }
}
