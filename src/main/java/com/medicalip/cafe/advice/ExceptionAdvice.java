package com.medicalip.cafe.advice;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.medicalip.cafe.advice.exception.CEmailSignFailedException;
import com.medicalip.cafe.common.util.Constants;
import com.medicalip.cafe.dto.response.CommonResult;
import com.medicalip.cafe.service.ResponseService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


/**
 * @Class : ExceptionAdvice
 * @Description : 도메인에 대한 공통 에러 처리
 **/
@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class ExceptionAdvice {
	
	private final ResponseService responseService; // API 요청 결과에 대한 code, message
	
	private final MessageSourceAccessor messageSourceAccessor;
	
	@ExceptionHandler(CEmailSignFailedException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	protected CommonResult emailSigninFailed(HttpServletRequest request, CEmailSignFailedException e) {
	    return responseService.getFailResult(Constants.EMAILSIGNINFAILED_CODE, Constants.EMAILSIGNINFAILED_MSG);
	}
}
