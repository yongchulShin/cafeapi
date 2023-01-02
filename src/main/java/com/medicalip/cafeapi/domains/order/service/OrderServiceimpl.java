package com.medicalip.cafeapi.domains.order.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.medicalip.cafeapi.domains.commons.response.CommonResult;
import com.medicalip.cafeapi.domains.menu.repo.MenuRepository;
import com.medicalip.cafeapi.domains.order.dto.BalanceSumInterface;
import com.medicalip.cafeapi.domains.order.dto.Order;
import com.medicalip.cafeapi.domains.order.dto.OrderDetail;
import com.medicalip.cafeapi.domains.order.dto.OrderRequest;
import com.medicalip.cafeapi.domains.order.dto.OrderResponse;
import com.medicalip.cafeapi.domains.order.repo.OrderDetailRepository;
import com.medicalip.cafeapi.domains.order.repo.OrderRepository;
import com.medicalip.cafeapi.domains.users.dto.Users;
import com.medicalip.cafeapi.domains.users.repo.UsersRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class OrderServiceimpl implements OrderService  {
	
	private final UsersRepository usersRepository;
	private final MenuRepository menuRepository;
	private final OrderRepository orderRepository;
	private final OrderDetailRepository orderDetailRepository;
	
	@Override
	public CommonResult orderFrom(OrderRequest orderRequest) {
		// TODO Auto-generated method stub
		System.out.println("[orderFrom]");
		Users users = usersRepository.findByEmail(orderRequest.getEmail())
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
		
//		List<OrderDetail> odList = new ArrayList<>();
		Order order = orderRepository.save(
				Order.builder()
				.users(users)
				.approvalYn("Y")
				.balanceYn("N")
				.edtTime(LocalDateTime.now())
				.regTime(LocalDateTime.now())
				.build());

		for(int i = 0; i < orderRequest.getOrderDetail().size(); i++) {
			OrderDetail od = orderDetailRepository.save(
							OrderDetail.builder()
								.menu(menuRepository.getById(orderRequest.getOrderDetail().get(i).getMenu().getId()))
								.cnt(orderRequest.getOrderDetail().get(i).getCnt())
								.order(order)
								.build());
		}
		
		return new CommonResult(200, "주문완료 하였습니다.");
		
	}
	
	@Override
	public Iterable<OrderDetail> orderDetailList(String email) {
		// TODO Auto-generated method stub
		if(email.isEmpty()) {
			return orderDetailRepository.findAll();
		}
		return orderDetailRepository.getOrderList(email);
	}

	@Override
	public Iterable<Order> getOrderList(String email) {
		// TODO Auto-generated method stub
		if(email.isEmpty()) {
			return orderRepository.getOrderListAll();
		}
		return orderRepository.getOrderList(email);
	}

	@Override
	public Iterable<Order> getbalanceList(String email) {
		// TODO Auto-generated method stub
//		if(email.isEmpty()) {
//			return orderRepository.getbalanceListAll();
//		}
		return orderRepository.getbalanceList(email);
	}
	
	@Override
	public List<BalanceSumInterface> getBalanceSum(String email) {
		// TODO Auto-generated method stub
		if(email.isEmpty()) {
			return orderRepository.getBalanceSumAll();
		}
		return orderRepository.getBalanceSum(email);
	}

	@Override
	public Iterable<Order> getApprovalList(String email) {
		// TODO Auto-generated method stub
		return orderRepository.getApprovalList(email);
	}

	@Override
	public int updateBalanceYn(String email) {
		// TODO Auto-generated method stub
		return orderRepository.updateBalanceYn(email);
	}

	@Override
	public List<BalanceSumInterface> getOrderSumList(String email) {
		// TODO Auto-generated method stub
		if(email.isEmpty()) {
			return orderRepository.getOrderSumListAll();
		}
		return orderRepository.getOrderSumList(email);
	}

}
