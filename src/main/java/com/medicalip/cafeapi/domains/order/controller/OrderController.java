package com.medicalip.cafeapi.domains.order.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.medicalip.cafeapi.domains.commons.response.CommonResult;
import com.medicalip.cafeapi.domains.order.dto.Order;
import com.medicalip.cafeapi.domains.order.dto.OrderDetail;
import com.medicalip.cafeapi.domains.order.dto.OrderRequest;
import com.medicalip.cafeapi.domains.order.dto.OrderResponse;
import com.medicalip.cafeapi.domains.order.service.OrderService;
import com.medicalip.cafeapi.domains.users.dto.Users;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/order")
public class OrderController {
	
	private final OrderService orderService;
	
	@PostMapping("/orderfrom")
	public CommonResult orderFrom(@RequestBody OrderRequest orderRequest) {
		System.out.println("orderFrom Controller");
		return orderService.orderFrom(orderRequest);
//		return null;
	}
	
	@GetMapping("/list")
	public ResponseEntity<Iterable<OrderDetail>> orderList(@RequestBody Map<String, String> map) {
		String email =  map.get("email");
		return ResponseEntity.ok().body(orderService.orderDetailList(email));
	}
	
	//미정산 내역(승인 : Y, 정산 : N)
	@GetMapping("/orderList")
	public ResponseEntity<Iterable<Order>> getOrderList(@RequestBody Map<String, String> map) {
		String email =  map.get("email");
		return ResponseEntity.ok().body(orderService.getOrderList(email));
	}
	
	//정산 내역(승인 : Y, 정산 : Y)
	@GetMapping("/balanceList")
	public ResponseEntity<Iterable<Order>> getbalanceList(@RequestBody Map<String, String> map) {
		String email =  map.get("email");
		return ResponseEntity.ok().body(orderService.getbalanceList(email));
	}

}
