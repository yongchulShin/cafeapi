package com.medicalip.cafeapi.domains.order.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.medicalip.cafeapi.domains.order.dto.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{
	
	@Query(value = "SELECT *"
			+ " FROM tb_order o join order_detail od on o.id = od.order_id"
			+ " join users u on o.users_id = u.id"
			+ " where u.email = :email"
			+ " group by o.id"
			, nativeQuery = true)
	Iterable<Order> getOrderList(@Param("email") String email);
	
}
