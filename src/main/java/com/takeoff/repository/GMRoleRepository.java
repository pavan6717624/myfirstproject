package com.takeoff.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.takeoff.domain.GMRole;

@Repository
public interface GMRoleRepository  extends JpaRepository<GMRole, Long> {
	Optional<GMRole> findByRoleName(@Param("roleName") String roleName);
}
