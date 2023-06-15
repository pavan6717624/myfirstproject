package com.takeoff.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.takeoff.domain.Statement;
import com.takeoff.model.StatementDTO;

@Repository
public interface StatementRepository extends JpaRepository<Statement,Long> {
	
	@Query("select s.amount as amount, s.date as date, s.description as description, s.customer.user.userId as customerId from Statement s where s.customer.user.userId=(:customerId) order by date asc")
	List<StatementDTO> takeOffStatment(@Param("customerId") Long customerId);
	
}
