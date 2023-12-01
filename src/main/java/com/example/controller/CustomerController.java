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

import com.example.dto.CustomerDto;
import com.example.model.Customer;
import com.example.response.CustomerResponse;
import com.example.service.CustomerService;

@RestController
@RequestMapping(value = "/om/v1/customer")
public class CustomerController {

	Logger logger = LoggerFactory.getLogger(CustomerController.class);

	@Autowired
	private CustomerService customerService;

	/*
	 * create/save-post update -put/patch read/retrieve-get delete-delete
	 * 
	 */
	@PostMapping(value = "/save") // @RequestMapping(value="/save,method=RequestMethod.POST")
	public ResponseEntity<?> createCustomer(@RequestBody CustomerDto customerDto) {
		logger.info("createCustomer Api has started");
		CustomerResponse customerResponse = new CustomerResponse();
		try {
			Customer saveCustomer = customerService.saveCustomer(customerDto);
			logger.info("customer save successfully");
			customerResponse.setData(saveCustomer);
			customerResponse.setMessage("customer saved successfully into database!");
			customerResponse.setCode(HttpStatus.OK.toString());

			return ResponseEntity.ok().body(customerResponse);
		} catch (Exception e) {
			logger.error("customer cannot be saved,an exception occured :" + e.getMessage());
			customerResponse.setData(null);
			customerResponse.setMessage("customer saved successfully into database!");
			customerResponse.setCode(HttpStatus.OK.toString());						

			return ResponseEntity.internalServerError().body(customerResponse);
		}

	}

	@GetMapping(value = "/find/{customerId}")
	public ResponseEntity<?> getCustomerById(@PathVariable("customerId") Long customerId) {
		logger.info("GetCustomer Api has started");
		CustomerResponse customerResponse = new CustomerResponse();
		try {
			Customer customer = customerService.getCustomerById(customerId);
			logger.info("customer fetched successfully");
			customerResponse.setData(customer);
			customerResponse.setMessage("customer is in  database!");
			customerResponse.setCode(HttpStatus.OK.toString());

			return ResponseEntity.ok().body(customerResponse);
		} catch (Exception e) {
			customerResponse.setData(null);
			customerResponse.setMessage("customer is in  database!");
			customerResponse.setCode(HttpStatus.OK.toString());
			logger.error("customer did not found");
			return ResponseEntity.internalServerError().body("Customer trying to find is not found inside database!");

		}

	}

	@GetMapping(value = "/findall")
	public ResponseEntity<?> getAllCustomer() {
		try {
			List<Customer> custList = customerService.getAllCustomer();

			return ResponseEntity.ok().body(custList);
		} catch (Exception e) {

			return ResponseEntity.internalServerError().body("no customer found");
		}
	}

	@GetMapping(value = "/findallwithpage")
	public ResponseEntity<?> getAllCusotmerWithPagination(@RequestParam("pageSize") Integer pagesize,
			@RequestParam("pageNumber") Integer pageNumber) {
		try {
			List<Customer> custList = customerService.getAllCusotmerWithPagination(pageNumber, pagesize);
			return ResponseEntity.ok().body(custList);
		} catch (Exception e) {
			return ResponseEntity.internalServerError().body("no customer found");
		}
	}

	@DeleteMapping(value = "/delete/{customerId}")
	public ResponseEntity<?> deleteCustomerById(@PathVariable("customerId") Long customerId) {
		try {
			customerService.deletetCustomerById(customerId);
			return ResponseEntity.ok().body("The customer with customerId " + customerId + "is deleted successfully");
		} catch (Exception e) {
			return ResponseEntity.internalServerError()
					.body("The customer with customerId " + customerId + "is not found");

		}
	}

	@GetMapping(value = "/findbyemail")
	public ResponseEntity<?> getCustomeWithEmail(@RequestParam("email") String email) {
		CustomerResponse customerResponse = new CustomerResponse();
		try {
			Customer customer = customerService.getCustomerWithEmail(email);
			customerResponse.setData(customer);
			customerResponse.setMessage("this data with email is in database");
			customerResponse.setCode(HttpStatus.OK.toString());

			return ResponseEntity.ok().body(customerResponse);
		} catch (Exception e) {
			customerResponse.setData(null);
			customerResponse.setMessage("this data with email is in database");
			customerResponse.setCode(HttpStatus.OK.toString());

			return ResponseEntity.internalServerError().body("Customer with email : " + email + " is not found");

		}
	}

	@GetMapping(value = "/findbysamecountry")
	public ResponseEntity<?> getCustomeWithSameCountry(@RequestParam("countryname") String countryname) {
		try {
			List<Customer> customerList = customerService.getCustomerWithSameCountry(countryname);

			return ResponseEntity.ok().body(customerList);
		} catch (Exception e) {
			return ResponseEntity.internalServerError().body("No customers present with country name : " + countryname);
		}
	}

	@PutMapping(value = "/update/{customerId}")
	public ResponseEntity<?> updateCustomer(@PathVariable("customerId") Long customerId,
			@RequestBody CustomerDto customerDto) {
		CustomerResponse customerResponse = new CustomerResponse();
		try {
			Customer updateCustomer = customerService.updateCustomer(customerId, customerDto);
			customerResponse.setData(updateCustomer);
			customerResponse.setMessage("this data hase been successfully update in database");
			customerResponse.setCode(HttpStatus.OK.toString());

			return ResponseEntity.ok().body(customerResponse);
		} catch (Exception e) {

			customerResponse.setData(null);
			customerResponse.setMessage("this data hase been successfully update in database");
			customerResponse.setCode(HttpStatus.OK.toString());

			return ResponseEntity.internalServerError().body("Update operation failed!");
		}

	}

	@GetMapping(value = "/count")
	public ResponseEntity<?> countCustomer() {
		try {
			long totalCustomer = customerService.countCustomer();

			return ResponseEntity.ok().body("The total number of customers present in database are " + totalCustomer);
		} catch (Exception e) {

			return ResponseEntity.internalServerError().body("No customer present");
		}
	}
}
