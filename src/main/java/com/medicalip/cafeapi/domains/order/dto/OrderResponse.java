package com.medicalip.cafeapi.domains.order.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderResponse {

	private String email;
	private String name;
	private long order_id;
	private long order_detail_id;
	private String menu;
	private int cnt;
	
}
