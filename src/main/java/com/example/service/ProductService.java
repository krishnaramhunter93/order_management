package com.example.service;

import java.util.List;

import com.example.dto.ProductDto;
import com.example.model.Product;

public interface ProductService {

	Product saveProduct(ProductDto ProductDto);

	Product getProductById(Long ProductId);

	List<Product> getAllProduct();

	List<Product> getAllProductWithPagination(Integer pageNumber, Integer pagesize);

	void deletetProductById(Long ProductId);




	Product updateProduct(Long ProductId, ProductDto ProductDto);

	long countProduct();

}
