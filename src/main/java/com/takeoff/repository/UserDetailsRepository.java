package com.takeoff.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.takeoff.domain.Roles;
import com.takeoff.domain.UserDetails;
import com.takeoff.model.VendorDetailsDTO;

@Repository
public interface UserDetailsRepository  extends JpaRepository<UserDetails,Long> {

	
	  @Query("select u from UserDetails u where u.userId=(:userId) and u.password = (:password) and u.isDeleted=false and u.isDisabled=false")
			Optional<UserDetails> findByUserIdAndPassword(@Param("userId") Long userId, @Param("password") String password);
	  
	  @Query("select u from UserDetails u where u.role = (:role) and u.isDeleted=false order by u.userId desc")
	  List<VendorDetailsDTO> findByRole(@Param("role") Roles role);
		
}
