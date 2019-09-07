package com.studyroom.studyroom.controller.exception;

import com.studyroom.studyroom.advice.exception.CustomAuthenticationEntryPointException;
import com.studyroom.studyroom.model.response.CommonResult;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/exception")
public class ExceptionController {

    // /exception/entrypoint 로 접근하면 해당 Exception 발생
    @GetMapping(value = "/entrypoint")
    public CommonResult entrypointexception() {
        throw new CustomAuthenticationEntryPointException();
    }
    @GetMapping(value = "/accessdenied")
    public CommonResult accessdeniedException() {
        throw new AccessDeniedException("");
    }
}
