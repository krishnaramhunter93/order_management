package com.example.response;

import com.example.model.Order;

import lombok.Data;

@Data
public class OrderResponse {
	
	private Order data;
	private String message;
	private String code;
	
	
}
