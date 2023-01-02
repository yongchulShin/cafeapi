package com.medicalip.cafeapi.domains.order.dto;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.medicalip.cafeapi.domains.menu.dto.Menu;

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
	
	@OneToOne(fetch = FetchType.EAGER, targetEntity = Menu.class)
	@JoinColumn(name = "menu_id")
	private Menu menu;
	
	private int cnt;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER, targetEntity = Order.class)
	@JoinColumn(name = "order_id")
	private Order order;
	
	@Builder
	public OrderDetail(Menu menu, int cnt, Order order) {
		this.menu = menu;
		this.cnt = cnt;
		this.order = order;
	}
	
	
	
}
