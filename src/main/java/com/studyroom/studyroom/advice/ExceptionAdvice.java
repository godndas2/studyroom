package com.studyroom.studyroom.advice;

import com.studyroom.studyroom.advice.exception.*;
import com.studyroom.studyroom.model.response.CommonResult;
import com.studyroom.studyroom.service.ResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
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
    private final MessageSource messageSource;

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) // 해당 Exception(500)이 발생하면 Response에 500 Error을 발생시킴
    protected CommonResult defaultException(HttpServletRequest request, Exception e) {
        return responseService.getFailedResult(Integer.valueOf(getMessage("unKnown.code")), getMessage("unKnown.msg"));
    }

    @ExceptionHandler(CustomUserNotFound.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult userNotFoundException(HttpServletRequest request, CustomUserNotFound customUserNotFound) {
        return responseService.getFailedResult(Integer.valueOf(getMessage("userNotFound.code")), getMessage("userNotFound.msg"));
    }

    @ExceptionHandler(CustomEmailSigninFailedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult emailSigninFailed(HttpServletRequest request, CustomEmailSigninFailedException e) {
        return responseService.getFailedResult(Integer.valueOf(getMessage("emailSigninFailed.code")), getMessage("emailSigninFailed.msg"));
    }

    @ExceptionHandler(CustomAuthenticationEntryPointException.class)
    public CommonResult authenticationEntryPointException(HttpServletRequest request, CustomAuthenticationEntryPointException e) {
        return responseService.getFailedResult(Integer.valueOf(getMessage("entryPointException.code")), getMessage("entryPointException.msg"));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public CommonResult AccessDeniedException(HttpServletRequest request, AccessDeniedException e) {
        return responseService.getFailedResult(Integer.valueOf(getMessage("accessDenied.code")), getMessage("accessDenied.msg"));
    }

    @ExceptionHandler(CustomCommunicationException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CommonResult communicationException(HttpServletRequest request, CustomCommunicationException e) {
        return responseService.getFailedResult(Integer.valueOf(getMessage("communicationError.code")), getMessage("communicationError.msg"));
    }

    @ExceptionHandler(CustomUserExistException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CommonResult communicationException(HttpServletRequest request, CustomUserExistException e) {
        return responseService.getFailedResult(Integer.valueOf(getMessage("existingUser.code")), getMessage("existingUser.msg"));
    }

    @ExceptionHandler(CustomNotOwnerException.class)
    @ResponseStatus(HttpStatus.NON_AUTHORITATIVE_INFORMATION)
    public CommonResult notOwnerException(HttpServletRequest request, CustomNotOwnerException e) {
        return responseService.getFailedResult(Integer.valueOf(getMessage("notOwner.code")), getMessage("notOwner.msg"));
    }

    @ExceptionHandler(CustomResourceNotExistException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public CommonResult resourceNotExistException(HttpServletRequest request, CustomResourceNotExistException e) {
        return responseService.getFailedResult(Integer.valueOf(getMessage("resourceNotExist.code")), getMessage("resourceNotExist.msg"));
    }

    // code 정보에 해당하는 msg를 조회
    private String getMessage(String code) {
        return getMessage(code, null);
    }
    // code 정보, 추가 argument로 현재 Locale에 맞는 msg를 조회
    private String getMessage(String code, Object[] args) {
        return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
    }

}
