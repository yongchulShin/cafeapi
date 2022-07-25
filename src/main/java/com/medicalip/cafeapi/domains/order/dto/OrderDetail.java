package com.medicalip.cafeapi.domains.order.dto;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@Table(name = "order_detail")
@Entity
public class OrderDetail {
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Long id;
	
	private String menu;
	private int cnt;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER, targetEntity = Order.class)
	@JoinColumn(name = "order_id")
	private Order order;
	
	@Builder
	public OrderDetail(String menu, int cnt, Order order) {
		this.menu = menu;
		this.cnt = cnt;
		this.order = order;
	}
	
	
	
}
