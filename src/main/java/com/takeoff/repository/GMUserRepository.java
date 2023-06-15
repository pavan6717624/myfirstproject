package com.takeoff.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.takeoff.domain.GMUser;
import com.takeoff.model.GMUserDTO;

@Repository
public interface GMUserRepository extends JpaRepository<GMUser, Long> {

	Optional<GMUser> findByContactAndPassword(@Param("contact") Long contact, @Param("password") String password);

	@Query("select u.id as id, u.name as name, u.contact as contact, u.email as email, u.city as city, u.message as message, u.loginId as loginId, u.role.roleName as roleName from GMUser u")
	List<GMUserDTO> getUsers();

	@Query("select u.id as id, u.name as name, u.contact as contact, u.email as email, u.city as city, u.message as message, u.loginId as loginId, u.role.roleName as roleName from GMUser u where u.role.roleName like 'Employee'")
	List<GMUserDTO> getEmployees();

	@Query("select u.id as id, u.name as name, u.contact as contact, u.email as email, u.city as city, u.message as message, u.loginId as loginId, u.role.roleName as roleName from GMUser u where u.role.roleName like 'Manager'")
	List<GMUserDTO> getManagers();

}
