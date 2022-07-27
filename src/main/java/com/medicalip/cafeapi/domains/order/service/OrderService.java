package com.medicalip.cafeapi.domains.order.service;

import java.util.List;

import com.medicalip.cafeapi.domains.commons.response.CommonResult;
import com.medicalip.cafeapi.domains.order.dto.BalanceSumInterface;
import com.medicalip.cafeapi.domains.order.dto.Order;
import com.medicalip.cafeapi.domains.order.dto.OrderDetail;
import com.medicalip.cafeapi.domains.order.dto.OrderRequest;

public interface OrderService {

	CommonResult orderFrom(OrderRequest orderRequest);

//	List<Order> orderList();
	Iterable<OrderDetail> orderDetailList(String email);
	
	Iterable<Order> getOrderList(String email);

	Iterable<Order> getbalanceList(String email);

	Iterable<Order> getApprovalList(String email);

	List<BalanceSumInterface> getBalanceSum(String email);

	int updateBalanceYn(String email);

	List<BalanceSumInterface> getOrderSumList(String email);

}
