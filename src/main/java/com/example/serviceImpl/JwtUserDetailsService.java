package com.example.serviceImpl;

import java.util.ArrayList;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		if (username.equals("jack")) {
			return new User("jack", new BCryptPasswordEncoder().encode("test"), new ArrayList<>());
		} else {
			throw new UsernameNotFoundException("user not found" + username);
		}

	}
	
	
	
	
	
	/*
	 * @Override public UserDetails loadUserByUsername(String username) throws
	 * UsernameNotFoundException { Customer customer =
	 * customerDao.findCustomerByEmail(username);
	 * if(username.equals(customer.getEmail())) { return new
	 * User(customer.getEmail(), new
	 * BCryptPasswordEncoder().encode(customer.getPassword()), new ArrayList<>()); }
	 * else { throw new UsernameNotFoundException("user not found : "+username); }
	 * 
	 * }
	 */

}
