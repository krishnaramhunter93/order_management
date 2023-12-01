package com.example.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.dto.CustomerDto;
import com.example.response.CustomerResponse;

@RestController
@RequestMapping(value = "/om/v2/customer")
public class CustomerRestTemplateController {

	private RestTemplate restTemplate = new RestTemplate();

	@GetMapping(value = "/findusadata")
	public ResponseEntity<?> getUSAData() {

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> httpEntity = new HttpEntity<String>("parameters", httpHeaders);
		ResponseEntity<?> response = restTemplate.exchange(
				"https://datausa.io/api/data?drilldowns=Nation&measures=Population", HttpMethod.GET, httpEntity,
				String.class);
		return response;
	}

	// Rest Template via rest api for post method using exchange
	@PostMapping(value = "/saveresttemplate")
	public ResponseEntity<?> createCustomerViaRestTemplate(@RequestBody CustomerDto customerDto) {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<CustomerDto> httpEntity = new HttpEntity<CustomerDto>(customerDto, httpHeaders);

		ResponseEntity<?> response = restTemplate.exchange("http://localhost:8081/om/v1/customer/save", HttpMethod.POST,
				httpEntity, String.class);

		return response;
	}

	@PutMapping(value = "/updateresttemplate/{customerId}")
	public ResponseEntity<?> updateCustomerViaRestTemplate(@PathVariable("customerId") Long customerId,
			@RequestBody CustomerDto customerDto) {

		HttpHeaders headers = new HttpHeaders();

		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

		HttpEntity<CustomerDto> httpEntity = new HttpEntity<CustomerDto>(customerDto, headers);

		ResponseEntity<?> response = restTemplate.exchange("http://localhost:8081/om/v1/customer/update/" + customerId,
				HttpMethod.PUT, httpEntity, String.class);
		return response;
	}

	@DeleteMapping(value = "/deleterest/{customerId}")
	public ResponseEntity<?> deleteCustomerViaResttemplate(@PathVariable("customerId") Long customerId) {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<?> httpEntity = new HttpEntity<>(headers);

		ResponseEntity<?> response = restTemplate.exchange("http://localhost:8081/om/v1/customer/delete/" + customerId,
				HttpMethod.DELETE, httpEntity, String.class);

		return response;
	}

	// Rest Template via rest api for get method using getforentity-get only work
	// here
	@GetMapping(value = "/findallasentity")
	public ResponseEntity<?> getAllCustomerAsEntity() {

		String endUrl = "http://localhost:8081/om/v1/customer/findall";

		ResponseEntity<?> response = restTemplate.getForEntity(endUrl, List.class);
		return response;
	}
	// Rest Template via rest api for get method using getforentity-get only work

	@GetMapping(value = "/findallobject")
	public List<?> getAllCustomerAsObject() {

		List<?> customerresponse = restTemplate.getForObject("http://localhost:8081/om/v1/customer/findall",
				List.class);
		return customerresponse;
	}

	// Rest template via rest api for post method using postForObject
	@PostMapping(value = "/saveCustomerasobject")
	public CustomerResponse createCustomerAsObject(@RequestBody CustomerDto customerDto) {
		HttpEntity<CustomerDto> customerDtoEntity = new HttpEntity<CustomerDto>(customerDto);

		CustomerResponse response = restTemplate.postForObject("http://localhost:8081/om/v1/customer/save", customerDtoEntity, CustomerResponse.class);
		return response;
	}

	@PostMapping(value = "/saveCustomerasentity")
	public ResponseEntity<?>  createCustomerAsPostEntity(@RequestBody CustomerDto customerDto) {
		HttpEntity<CustomerDto> customerDtoEntity = new HttpEntity<CustomerDto>(customerDto);

		ResponseEntity<?> response = restTemplate.postForEntity("http://localhost:8081/om/v1/customer/save", customerDtoEntity, CustomerResponse.class);
		return response;
	}
}
