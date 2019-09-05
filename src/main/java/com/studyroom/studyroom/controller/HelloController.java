package com.studyroom.studyroom.controller;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

    @GetMapping(value = "/hello/string")
    @ResponseBody // 결과를 응답에 그대로 출력
    public String hello() {
        return "Hello World";
    }

    @GetMapping(value = "/hello/json")
    @ResponseBody
    public Hello helloJson() {
        Hello hello = new Hello();
        hello.message = "Hello World";
        return hello;
    }

    @GetMapping(value = "/hello/page")
    public String helloFreemarker() {
        return "helloworld";
    }

    @Getter
    @Setter
    public static class Hello {
        private String message;
    }

}
