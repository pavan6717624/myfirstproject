package com.takeoff.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.takeoff.domain.HeidigiUser;

public interface HeidigiUserRepository extends JpaRepository<HeidigiUser, Long> {

	Optional<HeidigiUser> findByMobileAndPassword(Long mobile, String password);
	
	@Query( nativeQuery = true, value = "select image from image_details order by rand() limit 365")
	List<String> getRandomImages();

}
