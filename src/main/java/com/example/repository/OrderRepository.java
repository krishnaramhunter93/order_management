package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

	@Query(nativeQuery = true, value = "select * from order where delivery_address like %:city")
	List<Order> getOrderWithSameCity(String city);

	@Query(nativeQuery = true, value = "select * from order where delivery_address like %:city")
	long countOrderByCity(String city);

}
