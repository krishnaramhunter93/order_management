package com.example.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.dto.CustomerDto;
import com.example.model.Customer;
import com.example.repository.CustomerRepository;
import com.example.serviceImpl.CustomerServiceImpl;

@RunWith(SpringRunner.class)
public class CustomerServiceImplTest {
	@InjectMocks
	private CustomerServiceImpl customerServiceImpl;

	@Mock
	private CustomerRepository customerRepository;

	@BeforeEach // before calling each test case this set up method will call
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	// this is a request payload
	private CustomerDto getCustomerRequest() {
		CustomerDto customerRequest = new CustomerDto();
		customerRequest.setCustomerName("test");
		customerRequest.setEmail("test123@gmail.com");
		customerRequest.setMobile("+98762543673788");
		customerRequest.setAddress("65, siiaj, djick, siksi");
		customerRequest.setPassword("test123");
		return customerRequest;
	}

	// this is a response object
	private Customer getCustomer() {
		Customer customer = new Customer();
		customer.setCustomerId(123L);
		customer.setCustomerName("test");
		customer.setEmail("test123@gmail.com");
		customer.setMobile("+98762543673788");
		customer.setAddress("65, siiaj, djick, siksi");
		customer.setPassword("test123");
		return customer;
	}

	@Test
	public void saveCustomerTestPositive() {
		// customerDao.save(customer);
		Mockito.when(customerRepository.save(Mockito.any())).thenReturn(getCustomer());
		Customer actualResponse = customerServiceImpl.saveCustomer(getCustomerRequest());
		assertNotNull(actualResponse);
		assertEquals(getCustomer(), actualResponse);
	}

	@Test
	public void saveCustomerTestNegative() {
		// customerDao.save(customer);
		Mockito.when(customerRepository.save(Mockito.any())).thenReturn(null);
		assertThrows(RuntimeException.class, () -> customerServiceImpl.saveCustomer(getCustomerRequest()));
	}

	@Test
	public void getCustomerByIdTestPositive() {
		// customerDao.findById(customerId);
		Mockito.when(customerRepository.findById(Mockito.any())).thenReturn(Optional.of(getCustomer()));
		Customer actualResponse = customerServiceImpl.getCustomerById(123L);
		assertNotNull(actualResponse);
		assertEquals(getCustomer(), actualResponse);
	}

	@Test
	public void getCustomerByIdTestNegative() {
		Mockito.when(customerRepository.findById(Mockito.any())).thenReturn(Optional.empty());
		assertThrows(RuntimeException.class, () -> customerServiceImpl.getCustomerById(234L));
	}

	@Test
	public void getAllCustomersTestPositive() {

		List<Customer> customersList = new ArrayList<>();
		customersList.add(getCustomer());
		// customerDao.findAll();
		Mockito.when(customerRepository.findAll()).thenReturn(customersList);
		List<Customer> actualcustomerList = customerServiceImpl.getAllCustomer();
		assertNotNull(actualcustomerList);
		assertEquals(customersList, actualcustomerList);
	}

	@Test
	public void getAllCustomersTestNegative() {
		List<Customer> customersList = new ArrayList<>();
		Mockito.when(customerRepository.findAll()).thenReturn(customersList);
		assertThrows(RuntimeException.class, () -> customerServiceImpl.getAllCustomer());
	}

	@Test
	public void getAllCustomersWithPaginationTestPositive() {
		List<Customer> customersList = new ArrayList<>();
		customersList.add(getCustomer());

		Page<Customer> pageCustomer = new PageImpl<Customer>(customersList);
		// customerDao.findAll(PageRequest.of(pageNumber,
		// pageSize,Sort.by("customerName").descending()));
		Mockito.when(customerRepository.findAll(PageRequest.of(0, 1, Sort.by("customerName").descending())))
				.thenReturn(pageCustomer);
		List<Customer> actualCustomerList = customerServiceImpl.getAllCusotmerWithPagination(0, 1);
		assertNotNull(actualCustomerList);
		assertEquals(customersList, actualCustomerList);
	}

	@Test
	public void getAllCustomersWithPaginationTestNegative() {
		List<Customer> customersList = new ArrayList<>();
		Page<Customer> pageCustomer = new PageImpl<Customer>(customersList);
		// customerDao.findAll(PageRequest.of(pageNumber,
		// pageSize,Sort.by("customerName").descending()));
		Mockito.when(customerRepository.findAll(PageRequest.of(0, 1, Sort.by("customerName").descending())))
				.thenReturn(pageCustomer);
		assertThrows(RuntimeException.class, () -> customerServiceImpl.getAllCusotmerWithPagination(0, 1));
	}

	@Test
	public void deleteCustomerByIdTestPositive() {
		customerServiceImpl.deletetCustomerById(12L);

	}
	
}
