package com.takeoff.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.takeoff.domain.Contacts;
@Repository
public interface ContactsRepository extends JpaRepository<Contacts,Long> {

}
