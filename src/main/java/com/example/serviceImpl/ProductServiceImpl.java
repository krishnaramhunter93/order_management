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

import com.example.dto.ProductDto;
import com.example.model.Product;
import com.example.repository.ProductRepository;
import com.example.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

	@Autowired
	private ProductRepository ProductRepository;

	public Product saveProduct(ProductDto ProductDto) {

		// takes all the fields from ProductRequest and set it in Product model object
		// then save Product

		logger.info("savedProduct method started");

		Product Product = new Product();

		Product.setPrice(ProductDto.getPrice());
		Product.setProductName(ProductDto.getProductName());
		Product.setProductQuantity(ProductDto.getProductQuantity());
		Product.setDescription(ProductDto.getDescription());

		Product = ProductRepository.save(Product);
		logger.info("saved successfully");
		if (Product == null) {
			throw new RuntimeException("Product not saved!");
		}

		return Product;

	}

	public Product getProductById(Long ProductId) {
		logger.info("getProductByid method has started");
		Optional<Product> ProductOptional = ProductRepository.findById(ProductId);
		if (!ProductOptional.isPresent()) {
			throw new RuntimeException("Product not found in database");
		}
		Product Product = ProductOptional.get();
		logger.info("getProductbyid method has ended");
		return Product;

	}

	public List<Product> getAllProduct() {
		List<Product> ProductList = ProductRepository.findAll();
		if (ProductList.isEmpty()) {
			throw new RuntimeException("no Product present in the database");
		}
		return ProductList;

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

	public List<Product> getAllProductWithPagination(Integer pageNumber, Integer pageSize) {
		Page<Product> pageProduct = ProductRepository
				.findAll(PageRequest.of(pageNumber, pageSize, Sort.by("ProductName").ascending()));

		// we need to convert page to list of Product and return list as result
		List<Product> ProductList = new ArrayList<Product>();

		for (Product cust : pageProduct) {
			ProductList.add(cust);
		}
		if (ProductList.isEmpty()) {
			throw new RuntimeException("no Product presetn in the database");
		}
		return ProductList;

	}

	public void deletetProductById(Long ProductId) {

		ProductRepository.deleteById(ProductId);

	}

	public Product updateProduct(Long ProductId, ProductDto newProductDto) {
		// 1.get old Product present in database using findbyid
		// 2.remove oldProduct details andset old Product details with newProduct
		// 3.save the new Product update details and return it
		Product updateProduct = null;
		Optional<Product> ProductOptional = ProductRepository.findById(ProductId);

		if (ProductOptional.isPresent()) {
			Product oldProduct = ProductOptional.get();
			oldProduct.setDescription(newProductDto.getDescription());
			oldProduct.setPrice(newProductDto.getPrice());
			oldProduct.setProductName(newProductDto.getProductName());
			oldProduct.setProductQuantity(newProductDto.getProductQuantity());

			updateProduct = ProductRepository.save(oldProduct);
			if (updateProduct == null) {
				throw new RuntimeException();
			}
		} else {
			throw new RuntimeException("Product not found");
		}

		return updateProduct;

	}

	public long countProduct() {
		long totalProduct = ProductRepository.count();
		if (totalProduct == 0) {
			throw new RuntimeException();
		}
		return totalProduct;
	}

}
