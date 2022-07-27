package com.medicalip.cafeapi.domains.order.dto;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import lombok.Getter;

@Getter
public class OrderRequest {
	
	@NotBlank(message = "Email은 필수 값입니다.")
	@NotEmpty(message = "Email은 필수 값입니다.")
	private String email;
	
	private List<OrderDetail> orderDetail;
	
}
