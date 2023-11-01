package com.takeoff.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.takeoff.domain.HeidigiImage;

@Repository
public interface HeidigiImageRepository  extends JpaRepository<HeidigiImage,Long> {
	
	
	@Query("select h from HeidigiImage h where h.type='Image'")
	List<HeidigiImage> getImageIds();

}
