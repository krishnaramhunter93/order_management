package com.example.dto;

import lombok.Data;
@Data
public class OrderDto {
	private String productPurchaseQuantity;
	private String totalPrice;
	private String orderStatus;
	private String deliveryAddress;
	private String paymentMethod;
	private long productId;
	private long customerId;

}
