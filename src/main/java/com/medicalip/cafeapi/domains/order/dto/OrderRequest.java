package com.medicalip.cafeapi.domains.order.dto;

import java.util.List;

import lombok.Getter;

@Getter
public class OrderRequest {
	
	private String email;
	private List<OrderDetail> orderDetail;
	
}
