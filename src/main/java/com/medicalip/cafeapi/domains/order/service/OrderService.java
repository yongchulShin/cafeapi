package com.medicalip.cafeapi.domains.order.service;

import com.medicalip.cafeapi.domains.commons.response.CommonResult;
import com.medicalip.cafeapi.domains.order.dto.Order;
import com.medicalip.cafeapi.domains.order.dto.OrderDetail;
import com.medicalip.cafeapi.domains.order.dto.OrderRequest;

public interface OrderService {

	CommonResult orderFrom(OrderRequest orderRequest);

//	List<Order> orderList();
	Iterable<OrderDetail> orderDetailList(String email);
	Iterable<Order> getOrderList(String email);

}
