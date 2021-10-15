package com.takeoff.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.takeoff.domain.ScanCode;

@Repository
public interface ScanCodeRepository extends JpaRepository<ScanCode,Long> {

	Optional<ScanCode> findByCode(@Param("scanCode") String scanCode);

}
