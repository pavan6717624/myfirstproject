package com.takeoff.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.takeoff.domain.Redemption;

@Repository
public interface RedemptionRepository extends JpaRepository<Redemption,Long> {

}
