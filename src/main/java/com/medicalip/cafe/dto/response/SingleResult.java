package com.medicalip.cafe.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SingleResult {
	
	private int resultCode;
    private String resultMessage;
    private String token;

}
