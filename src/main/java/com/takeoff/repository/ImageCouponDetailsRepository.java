package com.takeoff.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.takeoff.domain.ImageDetails;

@Repository
public interface ImageCouponDetailsRepository  extends JpaRepository<ImageDetails,Long> {

	@Query("select i from ImageDetails i where i.user.userId = (:userId) order by id desc")
	List<ImageDetails> findByLatest(@Param("userId") Long userid);

}
