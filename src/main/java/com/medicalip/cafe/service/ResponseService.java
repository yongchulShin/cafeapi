package com.medicalip.cafe.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.medicalip.cafe.dto.response.CommonResult;
import com.medicalip.cafe.dto.response.SingleResult;

@Service
public class ResponseService {

	public CommonResult getSuccessResult() {
		// TODO Auto-generated method stub
		CommonResult cr = new CommonResult();
		
		cr.setResultCode(200);
		cr.setResultMessage("회원가입에 성공하였습니다.");
		
		return cr;
	}
	
	//
	public SingleResult loginSuccess(String createToken) {
		// TODO Auto-generated method stub
		SingleResult sr = new SingleResult();
		if(createToken != null) {
			sr.setResultCode(200);
			sr.setResultMessage("로그인에 성공하였습니다.");
			sr.setToken(createToken);
		}
		return sr;
	}
	
	public SingleResult loginFail(HttpStatus req) {
		// TODO Auto-generated method stub
		SingleResult sr = new SingleResult();
		sr.setResultCode(req.value());
		sr.setToken(null);
		if(req.value() == 400) {
			sr.setResultMessage("로그인 정보가 일치하지 않습니다.");
		}else if(req.value() == 500){
			sr.setResultMessage("내부서버오류. 잠시후 다시 시도해주세요.");
		}
		return sr;
	}

	public CommonResult getFailResult(Integer valueOf, String message) {
		// TODO Auto-generated method stub
		CommonResult cr = new CommonResult();
		
		cr.setResultCode(valueOf);
		cr.setResultMessage(message);
		
		return cr;
	}

}
