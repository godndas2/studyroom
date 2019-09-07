package com.studyroom.studyroom.advice;

import com.studyroom.studyroom.advice.exception.CustomUserNotFound;
import com.studyroom.studyroom.model.response.CommonResult;
import com.studyroom.studyroom.service.ResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/*
* ControllerAdvice는 Controller의 특정 패키지나 Anootation을 지정하면
* 해당 Controller들에 전역으로 적용되는 코드를 작성할 수 있다.
* 현재는, 예외처리를 공통 코드로 분리하기 위함이다.
* */
@RestControllerAdvice // 프로젝트 모든 controller에 적용
@RequiredArgsConstructor
public class ExceptionAdvice {

    private final ResponseService responseService;

//    @ExceptionHandler(Exception.class)
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) // 해당 Exception(500)이 발생하면 Response에 500 Error을 발생시킴
//    protected CommonResult defaultException(HttpServletRequest request, Exception e) {
//        return responseService.getFailedResult();
//    }

    @ExceptionHandler(CustomUserNotFound.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult userNotFoundException(HttpServletRequest request, CustomUserNotFound customUserNotFound) {
        return responseService.getFailedResult();
    }


}
