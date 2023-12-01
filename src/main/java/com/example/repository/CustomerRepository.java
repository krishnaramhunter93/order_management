package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.model.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

	@Query(nativeQuery = true, value = "select * from customer where email= :email")
	public Customer findCustomerByEmail(String email);

	@Query(nativeQuery = true, value = "select * from customer where address like %:countryname")
	public List<Customer> findCustomerWithSameCountry(String countryname);

}
