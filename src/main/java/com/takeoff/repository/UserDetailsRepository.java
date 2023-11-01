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
	  
	  @Query("select u from UserDetails u where u.role = (:role) and u.isDeleted=false and u.isDisabled=false order by u.userId desc")
	  List<VendorDetailsDTO> findByRole(@Param("role") Roles role);
	
	 @Query("select u from UserDetails u where u.loginId=(:userId) and u.isDeleted=false and u.isDisabled=false")
	  Optional<UserDetails> isUser(@Param("userId") Long userId);
	
	 @Query("select u from UserDetails u where u.loginId=(:executiveId) and u.isDeleted=false and u.isDisabled=false")
	Optional<UserDetails> findByExecutiveId(Long executiveId);
	 
	 @Query("select u from UserDetails u where u.contact=(:username) and u.role.id=2 and u.isDeleted=false and u.isDisabled=false")
	List<UserDetails> findByContactNumber(@Param("username") String username);
		
	 @Query("select u from UserDetails u where u.email=(:email) and u.role.id=2 and u.isDeleted=false and u.isDisabled=false")
		List<UserDetails> findByEmail(@Param("email") String email);

	Optional<UserDetails> findByLoginId(@Param("loginId") Long loginId);
	
	Optional<UserDetails> findByUserId(@Param("userId") Long userId);
	
	@Query("select u from UserDetails u where (u.contact=(:userId) or u.userId=(:userId) or u.loginId=(:userId)) and u.email=(:email) and u.city=(:city) and u.isDeleted=false and u.isDisabled=false")
	Optional<UserDetails> checkForPasswordChange(String userId, String email, String city);
	
	@Query("select u from UserDetails u where u.contact=(:username) and u.role.id=(:role) and u.isDeleted=false and u.isDisabled=false")
	List<UserDetails> findByContactNumber(String username, Long role);

	
}
	