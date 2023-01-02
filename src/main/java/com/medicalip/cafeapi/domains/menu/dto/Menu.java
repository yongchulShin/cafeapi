package com.medicalip.cafeapi.domains.menu.dto;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.medicalip.cafeapi.domains.order.dto.Order;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@Table(name = "menu")
@Entity
public class Menu {

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Long id;
	
	@ManyToOne(fetch = FetchType.EAGER, targetEntity = MenuCategory.class)
	@JoinColumn(name = "menu_category_id")
	private MenuCategory menuCategory;
	
	@Column
	private String menuName;
	
	@Column
	private String menuNameEn;
	
	@Column
	private String iceYn;
	
	@Column
	private String hotYn;
	
	@Column
	private String useYn;
	
	@Column
	private String payment;
	
	@Column
	@JsonIgnore
	private LocalDateTime regTime;
	
	@Column
	@JsonIgnore
    private LocalDateTime edtTime;
	
}
