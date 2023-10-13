package com.takeoff.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.takeoff.domain.HeidigiUser;

public interface HeidigiUserRepository extends JpaRepository<HeidigiUser, Long> {

	Optional<HeidigiUser> findByMobileAndPassword(Long mobile, String password);

}
