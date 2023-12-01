package com.example.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.dto.OrderDto;
import com.example.enums.OrderStatus;
import com.example.enums.PaymentMethod;
import com.example.model.Customer;
import com.example.model.Order;
import com.example.model.Product;
import com.example.repository.OrderRepository;
import com.example.service.CustomerService;
import com.example.service.OrderService;
import com.example.service.ProductService;

@Service
public class OrderServiceImpl implements OrderService {

	Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

	@Autowired
	private OrderRepository OrderRepository;

	@Autowired
	private CustomerService customerService;
	@Autowired
	private ProductService productService;

	public Order saveOrder(OrderDto OrderDto) {

		// takes all the fields from OrderRequest and set it in Order model object
		// then save Order

		logger.info("savedOrder method started");

		Order order = new Order();

		order.setProductPurchaseQuantity(OrderDto.getProductPurchaseQuantity());
		// order.setOrderStatus(OrderDto.getOrderStatus());
		order.setDeliveryAddress(OrderDto.getDeliveryAddress());
		order.setPaymentMethod(OrderDto.getPaymentMethod());
		// order.setTotalPrice(OrderDto.getTotalPrice());

		if (PaymentMethod.ONLINE_TRANSACTION.toString().equalsIgnoreCase(OrderDto.getPaymentMethod())) {
			order.setPaymentMethod(PaymentMethod.ONLINE_TRANSACTION.toString());
		} else if (PaymentMethod.CREDIT_CARD.toString().equalsIgnoreCase(OrderDto.getPaymentMethod())) {
			order.setPaymentMethod(PaymentMethod.CREDIT_CARD.toString());
		} else if (PaymentMethod.DEBIT_CARD.toString().equalsIgnoreCase(OrderDto.getPaymentMethod())) {
			order.setPaymentMethod(PaymentMethod.DEBIT_CARD.toString());
		} else {
			order.setPaymentMethod(PaymentMethod.CASH_ON_DELIVERY.toString());
		}

		order.setOrderStatus(OrderStatus.IN_PROGRESS.toString());

		Customer customer = customerService.getCustomerById(OrderDto.getCustomerId());
		order.setCustomer(customer);

		Product product = productService.getProductById(OrderDto.getProductId());
		order.setProduct(product);

		int productPrice = Integer.parseInt(product.getPrice());

		int productPurshQunty = Integer.parseInt(OrderDto.getProductPurchaseQuantity());

		int totalPrice = productPrice * productPurshQunty;

		order.setTotalPrice(String.valueOf(totalPrice));
		// Order.setCustomer(OrderDto.getCustomer());
		// Order.setProduct(OrderDto.getProduct());

		order = OrderRepository.save(order);
		logger.info("saved successfully");
		if (order == null) {
			throw new RuntimeException("Order not saved!");
		}

		return order;

	}

	public Order getOrderById(Long OrderId) {
		logger.info("getOrderByid method has started");
		Optional<Order> OrderOptional = OrderRepository.findById(OrderId);
		if (!OrderOptional.isPresent()) {
			throw new RuntimeException("Order not found in database");
		}
		Order Order = OrderOptional.get();
		logger.info("getOrderbyid method has ended");
		return Order;

	}

	public List<Order> getAllOrder() {
		List<Order> OrderList = OrderRepository.findAll();
		if (OrderList.isEmpty()) {
			throw new RuntimeException("no Order present in the database");
		}
		return OrderList;

	}

	/*
	 * 
	 * pagination-fetching the records based on pages two parameters - page
	 * number,page size page number -total number of pages
	 *
	 * page size how many records needs to be present in each page page size-10,no
	 * of records -36 0th page -1-10 1st page -11-20 2nd page -21-30
	 *
	 * 3rd page-31-36
	 *
	 * pagesize-3,number of records -25 0th page-1-3 1st page-4-6 2nd page-7-9 3rd
	 * page-10-12 4th page -13-15 5th page-16-18 6th page-19-21 7th page-22-24 8th
	 * page-25
	 *
	 */

	public List<Order> getAllOrderWithPagination(Integer pageNumber, Integer pageSize) {
		Page<Order> pageOrder = OrderRepository
				.findAll(PageRequest.of(pageNumber, pageSize, Sort.by("OrderName").ascending()));

		// we need to convert page to list of Order and return list as result
		List<Order> OrderList = new ArrayList<Order>();

		for (Order cust : pageOrder) {
			OrderList.add(cust);
		}
		if (OrderList.isEmpty()) {
			throw new RuntimeException("no Order presetn in the database");
		}
		return OrderList;

	}

	public void deletetOrderById(Long OrderId) {

		OrderRepository.deleteById(OrderId);

	}

	public Order updateOrder(Long OrderId, OrderDto newOrderDto) {
		// 1.get old Order present in database using findbyid
		// 2.remove oldOrder details andset old Order details with newOrder
		// 3.save the new Order update details and return it
		Order updateOrder = null;
		Optional<Order> OrderOptional = OrderRepository.findById(OrderId);

		if (OrderOptional.isPresent()) {
			Order oldOrder = OrderOptional.get();
			oldOrder.setProductPurchaseQuantity(newOrderDto.getProductPurchaseQuantity());

			if (OrderStatus.SHIPPED.toString().equalsIgnoreCase(newOrderDto.getOrderStatus())) {
				oldOrder.setOrderStatus(OrderStatus.SHIPPED.toString());
			} else if (OrderStatus.DELIVERED.toString().equalsIgnoreCase(newOrderDto.getOrderStatus())) {
				oldOrder.setOrderStatus(OrderStatus.DELIVERED.toString());
			} else if (OrderStatus.RETURNED.toString().equalsIgnoreCase(newOrderDto.getOrderStatus())) {
				oldOrder.setOrderStatus(OrderStatus.RETURNED.toString());
			} else if (OrderStatus.CANCELLED.toString().equalsIgnoreCase(newOrderDto.getOrderStatus())) {
				oldOrder.setOrderStatus(OrderStatus.CANCELLED.toString());
			} else {
				oldOrder.setOrderStatus(OrderStatus.IN_PROGRESS.toString());
			}

			oldOrder.setOrderStatus(newOrderDto.getOrderStatus());
			oldOrder.setDeliveryAddress(newOrderDto.getDeliveryAddress());
			// oldOrder.setPaymentMethod(newOrderDto.getPaymentMethod());
			if (PaymentMethod.ONLINE_TRANSACTION.toString().equalsIgnoreCase(newOrderDto.getPaymentMethod())) {
				oldOrder.setPaymentMethod(PaymentMethod.ONLINE_TRANSACTION.toString());
			} else if (PaymentMethod.CREDIT_CARD.toString().equalsIgnoreCase(newOrderDto.getPaymentMethod())) {
				oldOrder.setPaymentMethod(PaymentMethod.CREDIT_CARD.toString());
			} else if (PaymentMethod.DEBIT_CARD.toString().equalsIgnoreCase(newOrderDto.getPaymentMethod())) {
				oldOrder.setPaymentMethod(PaymentMethod.DEBIT_CARD.toString());
			} else {
				oldOrder.setPaymentMethod(PaymentMethod.CASH_ON_DELIVERY.toString());
			}
			// oldOrder.setTotalPrice(newOrderDto.getTotalPrice());
			// oldOrder.setCustomer(newOrderDto.getCustomer());
			// oldOrder.setProduct(newOrderDto.getProduct());
			Customer customer = customerService.getCustomerById(newOrderDto.getCustomerId());
			oldOrder.setCustomer(customer);

			Product product = productService.getProductById(newOrderDto.getProductId());
			oldOrder.setProduct(product);

			// get the price of the product and convert it to integer
			int productPrice = Integer.parseInt(product.getPrice());
			// convert the productpurchasequantity to integer
			int prodPurshQnty = Integer.parseInt(newOrderDto.getProductPurchaseQuantity());
			// now calculate totalprice and set it
			int totalPrice = productPrice * prodPurshQnty;
			oldOrder.setTotalPrice(String.valueOf(totalPrice));

			updateOrder = OrderRepository.save(oldOrder);
			if (updateOrder == null) {
				throw new RuntimeException();
			}
		} else {
			throw new RuntimeException("Order not found");
		}

		return updateOrder;

	}

	public long countOrder() {
		long totalOrder = OrderRepository.count();
		if (totalOrder == 0) {
			throw new RuntimeException();
		}
		return totalOrder;
	}

	public List<Order> getOrderWithSameCity(String city) {
		List<Order> orderList = OrderRepository.getOrderWithSameCity(city);
		if (orderList.isEmpty()) {
			throw new RuntimeException();
		}
		return orderList;
	}

	@Override
	public long countOrderByCity(String city) {
		long totalOrderCity = OrderRepository.countOrderByCity(city);
		if (totalOrderCity == 0) {
			throw new RuntimeException();
		}
		return totalOrderCity;
	}
}
