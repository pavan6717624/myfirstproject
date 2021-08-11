package com.takeoff.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.takeoff.domain.Roles;

@Repository
public interface RolesRepository extends JpaRepository<Roles,Long> {
	
	Optional<Roles> findByRoleName(String roleName);
}
