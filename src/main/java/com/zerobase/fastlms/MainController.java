package com.zerobase.fastlms;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class MainController {

    @RequestMapping("/")
    public String index() {
        return "index";
    }
//    @GetMapping("/hello")
//    public String hello() {
//        String msg = "222";
//        return msg;
//    }
}
