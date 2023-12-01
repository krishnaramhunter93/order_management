package com.example.service;

import java.util.List;

import com.example.dto.OrderDto;
import com.example.model.Order;

public interface OrderService {

	Order saveOrder(OrderDto OrderDto);

	Order getOrderById(Long OrderId);

	List<Order> getAllOrder();

	List<Order> getAllOrderWithPagination(Integer pageNumber, Integer pagesize);

	void deletetOrderById(Long OrderId);

	Order updateOrder(Long OrderId, OrderDto OrderDto);

	long countOrder();

	List<Order> getOrderWithSameCity(String city);

	long countOrderByCity(String city);

}
