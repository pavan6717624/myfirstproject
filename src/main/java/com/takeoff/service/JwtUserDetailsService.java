package com.takeoff.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.takeoff.repository.UserDetailsRepository;

@Service
public class JwtUserDetailsService implements UserDetailsService {
	
	@Autowired
	UserDetailsRepository userDetailsRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		//System.out.println("entered in  loadUserByUsername..." + username);
		
		Optional<com.takeoff.domain.UserDetails> user = userDetailsRepository.findById(Long.valueOf(username));
		
		List<GrantedAuthority> roles =new ArrayList<GrantedAuthority>();
		
		if (user.isPresent()) {
			System.out.println("User is " +user.get().toString()+" "+user.get().getRole().getRoleName());
			roles.add(new SimpleGrantedAuthority(user.get().getRole().getRoleName()));
			return new User(user.get().getUserId()+"", user.get().getPassword(),roles);
		} else {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
		
	}
}
