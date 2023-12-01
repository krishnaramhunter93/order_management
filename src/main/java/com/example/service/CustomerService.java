package com.example.service;

import java.util.List;

import com.example.dto.CustomerDto;
import com.example.model.Customer;

public interface CustomerService {

	Customer saveCustomer(CustomerDto customerDto);

	Customer getCustomerById(Long customerId);

	List<Customer> getAllCustomer();

	List<Customer> getAllCusotmerWithPagination(Integer pageNumber, Integer pagesize);

	void deletetCustomerById(Long customerId);

	Customer getCustomerWithEmail(String email);

	List<Customer> getCustomerWithSameCountry(String countryname);

	Customer updateCustomer(Long customerId, CustomerDto customerDto);

	long countCustomer();

}
