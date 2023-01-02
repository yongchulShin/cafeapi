package com.medicalip.cafeapi.domains.menu.dto;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@Table(name = "menu_category")
@Entity
public class MenuCategory {

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Long id;
	
	@Column
	private String categoryName;
	
	@Column
	@JsonIgnore
    private LocalDateTime regTime;
	
	@Column
	@JsonIgnore
    private LocalDateTime edtTime;
}
