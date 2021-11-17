package com.takeoff.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.takeoff.domain.HitsReceived;

@Repository
public interface HitsReceivedRepository extends JpaRepository<HitsReceived,Long> {

	@Query("select h from HitsReceived h order by hitOn desc")
	List<HitsReceived> getHitsReceived();

}
