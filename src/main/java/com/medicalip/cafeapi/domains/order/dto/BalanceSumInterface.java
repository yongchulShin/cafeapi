package com.medicalip.cafeapi.domains.order.dto;

import java.time.LocalDateTime;

public interface BalanceSumInterface {
	Integer getUsersId(); 
	String getName();
	String getEmail();
	Integer getOrderCnt();
	Integer getOrderAmount();
	LocalDateTime getEdtTime();
}
