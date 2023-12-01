package com.example.response;

import com.example.model.Product;

import lombok.Data;

@Data
public class ProductResponse {
	
	private Product data;
	private String message;
	private String code;
	
	
}
