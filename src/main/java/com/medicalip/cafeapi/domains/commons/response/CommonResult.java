package com.medicalip.cafeapi.domains.commons.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommonResult {
	
    private int resultCode;
    private String resultMessage;
    
    public CommonResult(int code, String message) {
    	this.resultCode = code;
    	this.resultMessage = message;
    }
}
