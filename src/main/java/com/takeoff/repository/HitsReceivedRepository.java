package com.takeoff.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.takeoff.domain.HitsReceived;

@Repository
public interface HitsReceivedRepository extends JpaRepository<HitsReceived,Long> {

}
