package com.medicalip.cafeapi.domains.order.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.medicalip.cafeapi.domains.users.dto.Users;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor // 인자를 모두 갖춘 생성자를 자동으로 생성한다.
@Builder
@Entity
@Table(name = "tb_order")
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@JsonIgnore
	@OneToOne
	@JoinColumn(name = "usersId")
	private Users users;
	
	@OneToMany(mappedBy = "order")
//	@JoinColumn(name = "orderDetailId")
//	private OrderDetail orderDetail;
	private List<OrderDetail> orderDetail = new ArrayList<>();
//	
	private String approvalYn;
	private String balanceYn;
	
	private LocalDateTime edtTime; // 
    private LocalDateTime regTime; //
}
