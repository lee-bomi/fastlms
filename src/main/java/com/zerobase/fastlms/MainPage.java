package com.zerobase.fastlms;

import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainPage {

    @GetMapping("/")
    public String index() {
        return "Index Page";
    }
    @GetMapping("/hello")
    public String hello() {
        String msg = "222";
        return msg;
    }
}
