package com.takeoff.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.takeoff.domain.UserDetails;

@Repository
public interface UserDetailsRepository  extends JpaRepository<UserDetails,Long> {

	
	  @Query("select u from UserDetails u where u.userId=(:userId) and u.password = (:password)")
			Optional<UserDetails> findByUserIdAndPassword(@Param("userId") Long userId, @Param("password") String password);
		
}
