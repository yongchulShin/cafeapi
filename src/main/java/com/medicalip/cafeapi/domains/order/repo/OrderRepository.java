package com.medicalip.cafeapi.domains.order.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.medicalip.cafeapi.domains.order.dto.BalanceSumInterface;
import com.medicalip.cafeapi.domains.order.dto.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{
	
	@Query(value = "SELECT *\r\n"
			+ " FROM tb_order o join order_detail od on o.id = od.order_id\r\n"
			+ " join users u on o.users_id = u.id\r\n"
			+ " where o.approval_yn = 'N'\r\n"
			+ " and u.email = :email\r\n"
			+ " group by o.id"
			, nativeQuery = true)
	Iterable<Order> getApprovalList(@Param("email") String email);
	
	@Query(value = "SELECT *\r\n"
			+ " FROM tb_order o join order_detail od on o.id = od.order_id\r\n"
			+ " join users u on o.users_id = u.id\r\n"
			+ " where o.approval_yn = 'Y'\r\n"
			+ " and o.balance_yn = 'N'\r\n"
			+ " and u.email = :email\r\n"
			+ " group by o.id"
			, nativeQuery = true)
	Iterable<Order> getOrderList(@Param("email") String email);
	
	@Query(value = "SELECT *\r\n"
			+ " FROM tb_order o join order_detail od on o.id = od.order_id\r\n"
			+ " join users u on o.users_id = u.id\r\n"
			+ " where o.approval_yn = 'Y'\r\n"
			+ "and o.balance_yn = 'N'\r\n"
			+ " group by o.id"
			, nativeQuery = true)
	Iterable<Order> getOrderListAll();
	
	@Query(value = ""
			+ " SELECT *\r\n"
			+ " FROM tb_order o join order_detail od on o.id = od.order_id\r\n"
			+ " join users u on o.users_id = u.id\r\n"
			+ " where o.approval_yn = 'Y'\r\n"
			+ " and o.balance_yn = 'Y'\r\n"
			+ " and u.email = :email\r\n"
			+ " group by o.id"
			, nativeQuery = true)
	Iterable<Order> getbalanceList(@Param("email") String email);
	
	@Query(value ="\r\n" 
			+ "select o.users_id as usersId\r\n"
			+ ", u.email as email\r\n"
			+ ", u.name as name\r\n"
			+ ", sum(od.cnt) as orderCnt\r\n"
			+ ", sum(od.cnt) * 500 as orderAmount\r\n"
			+ ", o.edt_time as edtTime\r\n"
			+ "from tb_order o \r\n"
			+ "left outer join order_detail od on od.order_id = o.id\r\n"
			+ "left outer join users u on u.id = o.users_id  \r\n"
			+ "where 1=1\r\n"
			+ "and o.approval_yn = 'Y'\r\n"
			+ "and o.balance_yn = 'Y'\r\n"
			+ "and u.email = :email\r\n"
			+ "group by usersId, email, name, edtTime"
			, nativeQuery = true)
	List<BalanceSumInterface> getBalanceSum(String email);
	
	@Query(value ="\r\n" 
			+ "select o.users_id as usersId\r\n"
			+ ", u.email as email\r\n"
			+ ", u.name as name\r\n"
			+ ", sum(od.cnt) as orderCnt\r\n"
			+ ", sum(od.cnt) * 500 as orderAmount\r\n"
			+ ", o.edt_time as edtTime\r\n"
			+ "from tb_order o \r\n"
			+ "left outer join order_detail od on od.order_id = o.id\r\n"
			+ "left outer join users u on u.id = o.users_id  \r\n"
			+ "where 1=1\r\n"
			+ "and o.approval_yn = 'Y'\r\n"
			+ "and o.balance_yn = 'Y'\r\n"
			+ "group by usersId, email, name, edtTime"
			, nativeQuery = true)
	List<BalanceSumInterface> getBalanceSumAll();
	
	@Modifying
    @Query(value = "\r\n"
    		+ "update tb_order o left outer join users u on o.users_id = u.id\r\n"
    		+ "set o.balance_yn = 'Y'\r\n"
    		+ "where 1=1\r\n"
    		+ "and u.email = :email\r\n"
    		+ "and o.approval_yn = 'Y'\r\n"
    		+ "and o.balance_yn = 'N'\r\n"
    		, nativeQuery = true)
    int updateBalanceYn(String email);

	
	@Query(value ="\r\n" 
			+ "select o.users_id as usersId\r\n"
			+ ", u.email as email\r\n"
			+ ", u.name as name\r\n"
			+ ", sum(od.cnt) as orderCnt\r\n"
			+ ", sum(od.cnt) * 500 as orderAmount\r\n"
			+ ", o.edt_time as edtTime\r\n"
			+ "from tb_order o \r\n"
			+ "left outer join order_detail od on od.order_id = o.id\r\n"
			+ "left outer join users u on u.id = o.users_id  \r\n"
			+ "where 1=1\r\n"
			+ "and o.approval_yn = 'Y'\r\n"
			+ "and o.balance_yn = 'N'\r\n"
			+ "and u.email = :email\r\n"
			+ "group by usersId, email, name, edtTime"
			, nativeQuery = true)
	List<BalanceSumInterface> getOrderSumList(String email);

	@Query(value ="\r\n" 
			+ "select o.users_id as usersId\r\n"
			+ ", u.email as email\r\n"
			+ ", u.name as name\r\n"
			+ ", sum(od.cnt) as orderCnt\r\n"
			+ ", sum(od.cnt) * 500 as orderAmount\r\n"
			+ ", o.edt_time as edtTime\r\n"
			+ "from tb_order o \r\n"
			+ "left outer join order_detail od on od.order_id = o.id\r\n"
			+ "left outer join users u on u.id = o.users_id  \r\n"
			+ "where 1=1\r\n"
			+ "and o.approval_yn = 'Y'\r\n"
			+ "and o.balance_yn = 'N'\r\n"
			+ "group by usersId, email, name, edtTime"
			, nativeQuery = true)
	List<BalanceSumInterface> getOrderSumListAll();
	
}
