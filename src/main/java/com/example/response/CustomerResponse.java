package com.example.response;

import com.example.model.Customer;

import lombok.Data;

@Data
public class CustomerResponse {
	
	private Customer data;
	private String message;
	private String code;
	
	
}
