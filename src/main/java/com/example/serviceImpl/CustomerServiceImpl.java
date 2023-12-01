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

import com.example.dto.CustomerDto;
import com.example.model.Customer;
import com.example.repository.CustomerRepository;
import com.example.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {

	Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);

	@Autowired
	private CustomerRepository customerRepository;

	public Customer saveCustomer(CustomerDto customerDto) {

		// takes all the fields from customerRequest and set it in customer model object
		// then save customer

logger.info("savedCustomer method started");

		Customer customer = new Customer();
		customer.setCustomerName(customerDto.getCustomerName());
		customer.setEmail(customerDto.getEmail());
		customer.setMobile(customerDto.getMobile());
		customer.setPassword(customerDto.getPassword());
		customer.setAddress(customerDto.getAddress());
		customer = customerRepository.save(customer);
		logger.info("saved successfully");
		if (customer == null) {
			throw new RuntimeException("Customer not saved!");
		}

		return customer;

	}

	public Customer getCustomerById(Long customerId) {
		logger.info("getcustomerByid method has started");
		Optional<Customer> customerOptional = customerRepository.findById(customerId);
		if (!customerOptional.isPresent()) {
			throw new RuntimeException("Customer not found in database");
		}
		Customer customer = customerOptional.get();
		logger.info("getCustomerbyid method has ended");
		return customer;

	}

	public List<Customer> getAllCustomer() {
		List<Customer> customerList = customerRepository.findAll();
		if(customerList.isEmpty()) {
			throw new RuntimeException("no customer present in the database");
		}
		return customerList;

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

	public List<Customer> getAllCusotmerWithPagination(Integer pageNumber, Integer pageSize) {
		Page<Customer> pageCustomer = customerRepository
				.findAll(PageRequest.of(pageNumber, pageSize, Sort.by("customerName").ascending()));

		// we need to convert page to list of customer and return list as result
		List<Customer> customerList = new ArrayList<Customer>();

		for (Customer cust : pageCustomer) {
			customerList.add(cust);
		}
		if (customerList.isEmpty()) {
			throw new RuntimeException("no customer presetn in the database");
		}
		return customerList;

	}

	public void deletetCustomerById(Long customerId) {

		customerRepository.deleteById(customerId);

	}

	public Customer getCustomerWithEmail(String email) {
		Customer customer = customerRepository.findCustomerByEmail(email);
		if (customer == null) {
			throw new RuntimeException();
		}
		return customer;
	}

	public List<Customer> getCustomerWithSameCountry(String countryname) {
		List<Customer> customerList = customerRepository.findCustomerWithSameCountry(countryname);
		if (customerList.isEmpty()) {
			throw new RuntimeException();
		}
		return customerList;
	}

	public Customer updateCustomer(Long customerId, CustomerDto newCustomerDto) {
		// 1.get old customer present in database using findbyid
		// 2.remove oldcustomer details andset old customer details with newcustomer
		// 3.save the new customer update details and return it
		Customer updateCustomer = null;
		Optional<Customer> customerOptional = customerRepository.findById(customerId);

		if (customerOptional.isPresent()) {
			Customer oldCustomer = customerOptional.get();
			oldCustomer.setEmail(newCustomerDto.getEmail());
			oldCustomer.setAddress(newCustomerDto.getAddress());
			oldCustomer.setCustomerName(newCustomerDto.getCustomerName());
			oldCustomer.setPassword(newCustomerDto.getPassword());
			oldCustomer.setMobile(newCustomerDto.getMobile());

			updateCustomer = customerRepository.save(oldCustomer);
			if (updateCustomer == null) {
				throw new RuntimeException();
			}
		} else {
			throw new RuntimeException("customer not found");
		}

		return updateCustomer;

	}

	public long countCustomer() {
		long totalCustomer = customerRepository.count();
		if (totalCustomer == 0) {
			throw new RuntimeException();
		}
		return totalCustomer;
	}

}
