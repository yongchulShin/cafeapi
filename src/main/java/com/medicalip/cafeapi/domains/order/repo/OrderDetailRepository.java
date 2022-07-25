package com.medicalip.cafeapi.domains.order.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.medicalip.cafeapi.domains.order.dto.OrderDetail;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
	@Query(value = "SELECT *"
			+ " FROM order_detail od join tb_order o on o.id = od.order_id"
			+ " join users u on o.users_id = u.id"
			+ " where u.email = :email"
			, nativeQuery = true)
	Iterable<OrderDetail> getOrderList(@Param("email") String email);
}
