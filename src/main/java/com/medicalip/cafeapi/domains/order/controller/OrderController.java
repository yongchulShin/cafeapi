package com.medicalip.cafeapi.domains.order.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.medicalip.cafeapi.domains.commons.response.CommonResult;
import com.medicalip.cafeapi.domains.order.dto.BalanceSumInterface;
import com.medicalip.cafeapi.domains.order.dto.Order;
import com.medicalip.cafeapi.domains.order.dto.OrderDetail;
import com.medicalip.cafeapi.domains.order.dto.OrderRequest;
import com.medicalip.cafeapi.domains.order.dto.OrderResponse;
import com.medicalip.cafeapi.domains.order.service.OrderService;
import com.medicalip.cafeapi.domains.users.dto.Users;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/order")
public class OrderController {
	
	private final OrderService orderService;
	
	@PostMapping("/orderfrom")
	@Operation(summary = "주문하기", description = "주문을 한다.")
	public CommonResult orderFrom(@RequestBody OrderRequest orderRequest) {
		System.out.println("orderFrom Controller");
		return orderService.orderFrom(orderRequest);
//		return null;
	}
	
	//승인 내역(승인 : N, 정산 : N)
	@PostMapping("/list")
	public ResponseEntity<Iterable<Order>> orderList(@RequestBody @Valid OrderRequest req) {
		Assert.isNull(req.getEmail(), "email은 필수값 입니다.");
		System.out.println("email :: " + req.getEmail());
		return ResponseEntity.ok().body(orderService.getApprovalList(req.getEmail()));
	}
	
	//미정산 내역(승인 : Y, 정산 : N)
	@PostMapping("/orderList")
	@Operation(summary = "회원 별 주문내역", description = "회원 별 주문내역 리스트를 조회한다.")
	public ResponseEntity<Iterable<Order>> getOrderList(@RequestBody OrderRequest req) {
		String email =  req.getEmail();
		System.out.println("email :: " + email);
		return ResponseEntity.ok().body(orderService.getOrderList(email));
	}
	
	//정산 내역(승인 : Y, 정산 : Y)
	@PostMapping("/orderSumList")
	@Operation(summary = "주문내역(관리자)", description = "주문내역(관리자) 리스트를 조회한다.")
	public ResponseEntity<List<BalanceSumInterface>> getorderSumList(@RequestBody OrderRequest req) {
		return ResponseEntity.ok().body(orderService.getOrderSumList(req.getEmail()));
	}	
	
	//정산 내역(승인 : Y, 정산 : Y)
	@PostMapping("/balanceList")
	public ResponseEntity<Iterable<Order>> getbalanceList(@RequestBody OrderRequest req) {
		String email =  req.getEmail();
		System.out.println("email :: " + email);
		return ResponseEntity.ok().body(orderService.getbalanceList(email));
	}
	
	//정산 내역(승인 : Y, 정산 : Y)
	@PostMapping("/balanceSumList")
	@Operation(summary = "정산내역(관리자)", description = "정산내역(관리자) 리스트를 조회한다.")
	public ResponseEntity<List<BalanceSumInterface>> getBalanceSum(@RequestBody OrderRequest req) {
		return ResponseEntity.ok().body(orderService.getBalanceSum(req.getEmail()));
	}
	
	//정산 하기
	@PostMapping("/updateBalanceYn")
	@Operation(summary = "회원 별 정산하기(관리자)", description = "선택한 회원의 정산여부를 Y로 업데이트 한다. ")
	public CommonResult updateBalanceYn(@NotBlank @NotEmpty @RequestBody OrderRequest req) {
		String email =  req.getEmail();
		if(email == null || email.equals("")) {
			return new CommonResult(400, "Email은 필수 값 입니다.");
		}else {
			int result = orderService.updateBalanceYn(email);
			System.out.println("result :: " + result);
			return new CommonResult(200, result + "건 정산 처리하였습니다.");
		}
	}

}
