package com.example.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.dto.CustomerDto;
import com.example.model.Customer;
import com.example.service.CustomerService;

@RunWith(SpringRunner.class)
public class CustomerControllerTest {

	@InjectMocks
	private CustomerController customerController;

	@Mock
	private CustomerService customerService;

	@BeforeEach // before calling each test case ,this setup method is called
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	private CustomerDto getCustomerDto() {
		CustomerDto customerDto = new CustomerDto();
		customerDto.setCustomerName("test");
		customerDto.setAddress("65,ssia,aaj");
		customerDto.setEmail("ram858@gmail.com");
		customerDto.setPassword("test@123");
		customerDto.setMobile("123456655");

		return customerDto;
	}

	private Customer getCustomer() {
		Customer customer = new Customer();
		customer.setCustomerId(123L);
		customer.setCustomerName("test");
		customer.setAddress("65,ssia,aaj");
		customer.setEmail("ram858@gmail.com");
		customer.setPassword("test@123");
		customer.setMobile("123456655");

		return customer;

	}

	@Test
	public void createCusotmerTestPositive() {
		ResponseEntity<?> actualResponse = customerController.createCustomer(getCustomerDto());
		// we need to see if we are calling any other class methods inside actual method
		// if we are calling we need use
		// Mokito.when().thenreturn();
		Mockito.when(customerService.saveCustomer(Mockito.any())).thenReturn(getCustomer());
		// Customer saveCustomer = customerService.saveCustomer(getCustomerDto());

		assertNotNull(actualResponse);
		assertEquals(HttpStatus.OK, actualResponse.getStatusCode());

	}

	@Test
	public void createCusotmerTestNegative() {

		Mockito.when(customerService.saveCustomer(Mockito.any())).thenThrow(new RuntimeException());

		ResponseEntity<?> actualResponse = customerController.createCustomer(getCustomerDto());
		// we need to see if we are calling any other class methods inside actual method
		// if we are calling we need use
		// Mokito.when().thenreturn();
		// Customer saveCustomer = customerService.saveCustomer(getCustomerDto());

		assertNotNull(actualResponse);
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, actualResponse.getStatusCode());

	}

	@Test
	public void getCustomerByIdTestPositive() {
		Mockito.when(customerService.getCustomerById(Mockito.any())).thenReturn(getCustomer());
		// Customer customer = customerService.getCustomerById(customerId);
		ResponseEntity<?> actualResponse = customerController.getCustomerById(123L);
		assertNotNull(actualResponse);
		assertEquals(getCustomer(), actualResponse.getBody());
	}

	@Test
	public void getCustomerByIdTestNegative() {
		Mockito.when(customerService.getCustomerById(Mockito.any())).thenThrow(new RuntimeException());
		// Customer customer = customerService.getCustomerById(customerId);
		ResponseEntity<?> actualResponse = customerController.getCustomerById(123L);
		assertNotNull(actualResponse);
		assertEquals("Customer trying to find is not found inside database!", actualResponse.getBody());
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, actualResponse.getStatusCode());
	}

	@Test
	public void getAllCustomerTestPositive() {
		List<Customer> customerList = new ArrayList<>();
		customerList.add(getCustomer());
		Mockito.when(customerService.getAllCustomer()).thenReturn(customerList);
		// List<Customer> custList = customerService.getAllCustomers();
		ResponseEntity<?> actualResponse = customerController.getAllCustomer();
		assertNotNull(actualResponse);
		assertEquals(customerList, actualResponse.getBody());
	}

	@Test
	public void getAllCustomerTestNegative() {
		Mockito.when(customerService.getAllCustomer()).thenThrow(new RuntimeException());
		// List<Customer> custList = customerService.getAllCustomers();
		ResponseEntity<?> actualResponse = customerController.getAllCustomer();
		assertNotNull(actualResponse);
		assertEquals("No customers found!", actualResponse.getBody());
	}

	@Test
	public void getAllCustomerWithPaginationTestPositive() {
		List<Customer> customerList = new ArrayList<>();
		customerList.add(getCustomer());
		Mockito.when(customerService.getAllCusotmerWithPagination(Mockito.any(), Mockito.any()))
				.thenReturn(customerList);
		// List<Customer> customerList =
		// customerService.getAllCustomersWithPagination(pageNumber, pageSize);
		ResponseEntity<?> actualResponse = customerController.getAllCusotmerWithPagination(1, 0);
		assertNotNull(actualResponse);
		assertEquals(customerList, actualResponse.getBody());
	}

	@Test
	public void getAllCustomerWithPaginationTestNegative() {
		Mockito.when(customerService.getAllCusotmerWithPagination(Mockito.any(), Mockito.any()))
				.thenThrow(new RuntimeException());
		// List<Customer> customerList =
		// customerService.getAllCustomersWithPagination(pageNumber, pageSize);
		ResponseEntity<?> actualResponse = customerController.getAllCusotmerWithPagination(1, 0);
		assertNotNull(actualResponse);
		assertEquals("No customers found!", actualResponse.getBody());
	}

	@Test
	public void deleteCustomerByIdTestPositive() {
		// customerService.deleteCustomerById(customerId);
		// we cannot mockito.when for this other class method - it is not returning any
		// type
		ResponseEntity<?> actualResponse = customerController.deleteCustomerById(123L);
		assertNotNull(actualResponse);
		assertEquals("The customer with customerId " + 123L + " is deleted successfully!", actualResponse.getBody());
	}

	@Test
	public void getCustomerWithEmailTestPositive() {
		Mockito.when(customerService.getCustomerWithEmail(Mockito.any())).thenReturn(getCustomer());
		// Customer customer = customerService.getCustomerWithEmail(email);
		ResponseEntity<?> actualResponse = customerController.getCustomeWithEmail("test@gmail.com");
		assertNotNull(actualResponse);
		assertEquals(getCustomer(), actualResponse.getBody());
	}

	@Test
	public void getCustomerWithEmailTestNegative() {
		Mockito.when(customerService.getCustomerWithEmail(Mockito.any())).thenThrow(new RuntimeException());
		// Customer customer = customerService.getCustomerWithEmail(email);
		ResponseEntity<?> actualResponse = customerController.getCustomeWithEmail("test123@gmail.com");
		assertNotNull(actualResponse);
		assertEquals("Customer with email : test123@gmail.com is not found", actualResponse.getBody());
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, actualResponse.getStatusCode());

	}

	@Test
	public void getCustomerWithSameCountryTestPositive() {
		List<Customer> customerList = new ArrayList<>();
		customerList.add(getCustomer());
		Mockito.when(customerService.getCustomerWithSameCountry(Mockito.any())).thenReturn(customerList);
		// List<Customer> customerList =
		// customerService.getCustomerWithSameCountry(countryname);
		ResponseEntity<?> actualResponse = customerController.getCustomeWithSameCountry("siksi");
		assertNotNull(actualResponse);
		assertEquals(customerList, actualResponse.getBody());
	}

	@Test
	public void getCustomerWithSameCountryTestNegative() {
		Mockito.when(customerService.getCustomerWithSameCountry(Mockito.any())).thenThrow(new RuntimeException());
		// List<Customer> customerList =
		// customerService.getCustomerWithSameCountry(countryname);
		ResponseEntity<?> actualResponse = customerController.getCustomeWithSameCountry("siksi");
		assertNotNull(actualResponse);
		assertEquals("No customers present with country name : siksi", actualResponse.getBody());
	}

	@Test
	public void countCustomersTestPositive() {
		Mockito.when(customerService.countCustomer()).thenReturn(1L);
		// long totalcustomer = customerService.countCustomers();
		ResponseEntity<?> actualResponse = customerController.countCustomer();
		assertNotNull(actualResponse);
		assertEquals("The total number of customers present in database are " + 1L, actualResponse.getBody());
	}

	@Test
	public void countCustomersTestNegative() {
		Mockito.when(customerService.countCustomer()).thenThrow(new RuntimeException());
		// long totalcustomer = customerService.countCustomers();
		ResponseEntity<?> actualResponse = customerController.countCustomer();
		assertNotNull(actualResponse);
		assertEquals("No customer present", actualResponse.getBody());
	}

	@Test
	public void updateCustomerTestPositive() {
		Mockito.when(customerService.updateCustomer(Mockito.any(), Mockito.any())).thenReturn(getCustomer());
		// Customer updatedCustomer = customerService.updateCustomer(customerId,
		// newCustomerRequest);
		ResponseEntity<?> actualResponse = customerController.updateCustomer(123L, getCustomerDto());
		assertNotNull(actualResponse);
		assertEquals(getCustomer(), actualResponse.getBody());
	}

	@Test
	public void updateCustomerTestNegative() {
		Mockito.when(customerService.updateCustomer(Mockito.any(), Mockito.any())).thenThrow(new RuntimeException());
		// Customer updatedCustomer = customerService.updateCustomer(customerId,
		// newCustomerRequest);
		ResponseEntity<?> actualResponse = customerController.updateCustomer(123L, getCustomerDto());
		assertNotNull(actualResponse);
		assertEquals("Update operation failed!", actualResponse.getBody());
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, actualResponse.getStatusCode());
	}

}
