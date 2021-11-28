package com.takeoff.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.takeoff.domain.UserDetails;
import com.takeoff.domain.VendorDetails;
import com.takeoff.model.VendorDetailsDTO;
@Repository
public interface VendorDetailsRepository extends JpaRepository<VendorDetails,Long> {
	
	
	@Query("select v from VendorDetails v where v.user.userId=(:userId)  and v.user.isDisabled=false and v.user.isDeleted=false")
	Optional<VendorDetails> findByUserId(@Param("userId") Long userId);
	
	@Query("select u from UserDetails u where u.userId=(:userId) and u.isDisabled=false and u.isDeleted=false")
	Optional<UserDetails> findByDesignerId(@Param("userId") Long userId);
	
	@Query("select v from VendorDetails v where v.user.isDeleted=false order by v.user.userId desc")
	List<VendorDetailsDTO> getVendors();
	
	@Query("select distinct concat('data:image/jpeg;base64,',v.logo) as logo from VendorDetails v "
			+ "where v.user.name in ('The Hangout','Green Trends','Beats Fitness','Neera The Bar & Restaurant',"
			+ "'1947 Hair & Beauty Studio','Drunken Monkey','Biba','AP03 Drive In','Naturals Lounge',"
			+ "'Relish kafe','Manal Restaurant','Arrow','Aroma Coffee House','Toni & Guy Essensuals',"
			+ "'Studio11 Salon & Spa','16 Degrees Fitness Home','Bawarchi By Nawaabs','Cream & Crispy Restaurant',"
			+ "'Vedavyas Yoga Studio','EEMOS unisex salon & Academy','Indian Terrian','Kalyan Residency','Digitalshala',"
			+ "'Naveen Martial Arts','Kids world','Warriors Men Wear','Sanjeevani Ayurveda center') and v.user.isDeleted!=true")
	List<String> getLogos();



}
