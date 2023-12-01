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

import com.example.dto.ProductDto;
import com.example.model.Product;
import com.example.response.ProductResponse;
import com.example.service.ProductService;

@RestController
@RequestMapping(value = "/om/v1/Product")
public class ProductController {

	Logger logger = LoggerFactory.getLogger(ProductController.class);

	@Autowired
	private ProductService ProductService;

	/*
	 * create/save-post update -put/patch read/retrieve-get delete-delete
	 * 
	 */
	@PostMapping(value = "/save") // @RequestMapping(value="/save,method=RequestMethod.POST")
	public ResponseEntity<?> createProduct(@RequestBody ProductDto ProductDto) {
		logger.info("createProduct Api has started");
		ProductResponse ProductResponse = new ProductResponse();
		try {
			Product saveProduct = ProductService.saveProduct(ProductDto);
			logger.info("Product save successfully");
			ProductResponse.setData(saveProduct);
			ProductResponse.setMessage("Product saved successfully into database!");
			ProductResponse.setCode(HttpStatus.OK.toString());

			return ResponseEntity.ok().body(ProductResponse);
		} catch (Exception e) {
			logger.error("Product cannot be saved,an exception occured :" + e.getMessage());
			ProductResponse.setData(null);
			ProductResponse.setMessage("Product saved successfully into database!");
			ProductResponse.setCode(HttpStatus.OK.toString());

			return ResponseEntity.internalServerError().body(ProductResponse);
		}

	}

	@GetMapping(value = "/find/{ProductId}")
	public ResponseEntity<?> getProductById(@PathVariable("ProductId") Long ProductId) {
		logger.info("GetProduct Api has started");
		ProductResponse ProductResponse = new ProductResponse();
		try {
			Product Product = ProductService.getProductById(ProductId);
			logger.info("Product fetched successfully");
			ProductResponse.setData(Product);
			ProductResponse.setMessage("Product is in  database!");
			ProductResponse.setCode(HttpStatus.OK.toString());

			return ResponseEntity.ok().body(ProductResponse);
		} catch (Exception e) {
			ProductResponse.setData(null);
			ProductResponse.setMessage("Product is in  database!");
			ProductResponse.setCode(HttpStatus.OK.toString());
			logger.error("Product did not found");
			return ResponseEntity.internalServerError().body("Product trying to find is not found inside database!");

		}

	}

	@GetMapping(value = "/findall")
	public ResponseEntity<?> getAllProduct() {
		try {
			List<Product> custList = ProductService.getAllProduct();

			return ResponseEntity.ok().body(custList);
		} catch (Exception e) {

			return ResponseEntity.internalServerError().body("no Product found");
		}
	}

	@GetMapping(value = "/findallwithpage")
	public ResponseEntity<?> getAllProductWithPagination(@RequestParam("pageSize") Integer pagesize,
			@RequestParam("pageNumber") Integer pageNumber) {
		try {
			List<Product> custList = ProductService.getAllProductWithPagination(pageNumber, pagesize);
			return ResponseEntity.ok().body(custList);
		} catch (Exception e) {
			return ResponseEntity.internalServerError().body("no Product found");
		}
	}

	@DeleteMapping(value = "/delete/{ProductId}")
	public ResponseEntity<?> deleteProductById(@PathVariable("ProductId") Long ProductId) {
		try {
			ProductService.deletetProductById(ProductId);
			return ResponseEntity.ok().body("The Product with ProductId " + ProductId + "is deleted successfully");
		} catch (Exception e) {
			return ResponseEntity.internalServerError()
					.body("The Product with ProductId " + ProductId + "is not found");

		}
	}

	@PutMapping(value = "/update/{ProductId}")
	public ResponseEntity<?> updateProduct(@PathVariable("ProductId") Long ProductId,
			@RequestBody ProductDto ProductDto) {
		ProductResponse ProductResponse = new ProductResponse();
		try {
			Product updateProduct = ProductService.updateProduct(ProductId, ProductDto);
			ProductResponse.setData(updateProduct);
			ProductResponse.setMessage("this data hase been successfully update in database");
			ProductResponse.setCode(HttpStatus.OK.toString());

			return ResponseEntity.ok().body(ProductResponse);
		} catch (Exception e) {

			ProductResponse.setData(null);
			ProductResponse.setMessage("this data hase been successfully update in database");
			ProductResponse.setCode(HttpStatus.OK.toString());

			return ResponseEntity.internalServerError().body("Update operation failed!");
		}

	}

	@GetMapping(value = "/count")
	public ResponseEntity<?> countProduct() {
		try {
			long totalProduct = ProductService.countProduct();

			return ResponseEntity.ok().body("The total number of Products present in database are " + totalProduct);
		} catch (Exception e) {

			return ResponseEntity.internalServerError().body("No Product present");
		}
	}
}
