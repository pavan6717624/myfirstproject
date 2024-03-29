
package com.takeoff.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.takeoff.domain.FnoStock;

@Repository
public interface FnoStockRepository extends JpaRepository<FnoStock, Long> {

}