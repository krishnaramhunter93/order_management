package com.example.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.OrderDto;
import com.example.model.Customer;
import com.example.model.Order;
import com.example.response.OrderResponse;
import com.example.service.OrderService;

@RestController
@RequestMapping(value = "/om/v1/Order")
public class OrderController {

	Logger logger = LoggerFactory.getLogger(OrderController.class);

	@Autowired
	private OrderService OrderService;

	/*
	 * create/save-post update -put/patch read/retrieve-get delete-delete
	 * 
	 */
	@PostMapping(value = "/save") // @RequestMapping(value="/save,method=RequestMethod.POST")
	public ResponseEntity<?> createOrder(@RequestBody OrderDto OrderDto) {
		logger.info("createOrder Api has started");
		OrderResponse OrderResponse = new OrderResponse();
		try {
			Order saveOrder = OrderService.saveOrder(OrderDto);
			logger.info("Order save successfully");
			OrderResponse.setData(saveOrder);
			OrderResponse.setMessage("Order saved successfully into database!");
			OrderResponse.setCode(HttpStatus.OK.toString());

			return ResponseEntity.ok().body(OrderResponse);
		} catch (Exception e) {
			logger.error("Order cannot be saved,an exception occured :" + e.getMessage());
			OrderResponse.setData(null);
			OrderResponse.setMessage("Order saved successfully into database!");
			OrderResponse.setCode(HttpStatus.OK.toString());

			return ResponseEntity.internalServerError().body(OrderResponse);
		}

	}

	@GetMapping(value = "/find/{OrderId}")
	public ResponseEntity<?> getOrderById(@PathVariable("OrderId") Long OrderId) {
		logger.info("GetOrder Api has started");
		OrderResponse OrderResponse = new OrderResponse();
		try {
			Order Order = OrderService.getOrderById(OrderId);
			logger.info("Order fetched successfully");
			OrderResponse.setData(Order);
			OrderResponse.setMessage("Order is in  database!");
			OrderResponse.setCode(HttpStatus.OK.toString());

			return ResponseEntity.ok().body(OrderResponse);
		} catch (Exception e) {
			OrderResponse.setData(null);
			OrderResponse.setMessage("Order is in  database!");
			OrderResponse.setCode(HttpStatus.OK.toString());
			logger.error("Order did not found");
			return ResponseEntity.internalServerError().body("Order trying to find is not found inside database!");

		}

	}

	@GetMapping(value = "/findall")
	public ResponseEntity<?> getAllOrder() {
		try {
			List<Order> custList = OrderService.getAllOrder();

			return ResponseEntity.ok().body(custList);
		} catch (Exception e) {

			return ResponseEntity.internalServerError().body("no Order found");
		}
	}

	@GetMapping(value = "/findallwithpage")
	public ResponseEntity<?> getAllOrderWithPagination(@RequestParam("pageSize") Integer pagesize,
			@RequestParam("pageNumber") Integer pageNumber) {
		try {
			List<Order> custList = OrderService.getAllOrderWithPagination(pageNumber, pagesize);
			return ResponseEntity.ok().body(custList);
		} catch (Exception e) {
			return ResponseEntity.internalServerError().body("no Order found");
		}
	}

	@DeleteMapping(value = "/delete/{OrderId}")
	public ResponseEntity<?> deleteOrderById(@PathVariable("OrderId") Long OrderId) {
		try {
			OrderService.deletetOrderById(OrderId);
			return ResponseEntity.ok().body("The Order with OrderId " + OrderId + "is deleted successfully");
		} catch (Exception e) {
			return ResponseEntity.internalServerError().body("The Order with OrderId " + OrderId + "is not found");

		}
	}

	@PutMapping(value = "/update/{OrderId}")
	public ResponseEntity<?> updateOrder(@PathVariable("OrderId") Long OrderId, @RequestBody OrderDto OrderDto) {
		OrderResponse OrderResponse = new OrderResponse();
		try {
			Order updateOrder = OrderService.updateOrder(OrderId, OrderDto);
			OrderResponse.setData(updateOrder);
			OrderResponse.setMessage("this data hase been successfully update in database");
			OrderResponse.setCode(HttpStatus.OK.toString());

			return ResponseEntity.ok().body(OrderResponse);
		} catch (Exception e) {

			OrderResponse.setData(null);
			OrderResponse.setMessage("this data hase been successfully update in database");
			OrderResponse.setCode(HttpStatus.OK.toString());

			return ResponseEntity.internalServerError().body("Update operation failed!");
		}

	}

	@GetMapping(value = "/count")
	public ResponseEntity<?> countOrder() {
		try {
			long totalOrder = OrderService.countOrder();

			return ResponseEntity.ok().body("The total number of Orders present in database are " + totalOrder);
		} catch (Exception e) {

			return ResponseEntity.internalServerError().body("No Order present");
		}
	}

	@GetMapping(value = "/findbysamecity")
	public ResponseEntity<?> getOrderWithSameCity(@RequestParam("city") String city) {
		try {
			List<Order> orderList = OrderService.getOrderWithSameCity(city);

			return ResponseEntity.ok().body(orderList);
		} catch (Exception e) {
			return ResponseEntity.internalServerError().body("No Order present with city name : " + city);
		}
	}
	
	@GetMapping(value = "/countwithcity")
	public ResponseEntity<?> countOrderByCity(@RequestParam String city) {
		try {
			long totalOrder = OrderService.countOrderByCity(city);

			return ResponseEntity.ok().body("The total number of Orders present in database are " + totalOrder);
		} catch (Exception e) {

			return ResponseEntity.internalServerError().body("No Order present");
		}
	}
}
