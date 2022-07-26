package com.medicalip.cafeapi.domains.order.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.medicalip.cafeapi.domains.order.dto.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{
	
	@Query(value = "SELECT *"
			+ " FROM tb_order o join order_detail od on o.id = od.order_id"
			+ " join users u on o.users_id = u.id"
			+ " where o.approval_yn = 'N'"
			+ " and u.email = :email"
			+ " group by o.id"
			, nativeQuery = true)
	Iterable<Order> getApprovalList(@Param("email") String email);
	
	@Query(value = "SELECT *"
			+ " FROM tb_order o join order_detail od on o.id = od.order_id"
			+ " join users u on o.users_id = u.id"
			+ " where o.approval_yn = 'Y'"
			+ " and u.email = :email"
			+ " group by o.id"
			, nativeQuery = true)
	Iterable<Order> getOrderList(@Param("email") String email);
	
	@Query(value = "SELECT *"
			+ " FROM tb_order o join order_detail od on o.id = od.order_id"
			+ " join users u on o.users_id = u.id"
			+ " where o.approval_yn = 'Y'"
			+ " group by o.id"
			, nativeQuery = true)
	Iterable<Order> getOrderListAll();
	
	@Query(value = "SELECT *"
			+ " FROM tb_order o join order_detail od on o.id = od.order_id"
			+ " join users u on o.users_id = u.id"
			+ " where o.approval_yn = 'Y'"
			+ " and o.balance_yn = 'Y'"
			+ " and u.email = :email"
			+ " group by o.id"
			, nativeQuery = true)
	Iterable<Order> getbalanceList(@Param("email") String email);
	
	@Query(value = "SELECT *"
			+ " FROM tb_order o join order_detail od on o.id = od.order_id"
			+ " join users u on o.users_id = u.id"
			+ " where o.approval_yn = 'Y'"
			+ " and o.balance_yn = 'Y'"
			+ " group by o.id"
			, nativeQuery = true)
	Iterable<Order> getbalanceListAll();
	
}
