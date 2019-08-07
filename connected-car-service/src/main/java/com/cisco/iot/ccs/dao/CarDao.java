package com.cisco.iot.ccs.dao;

import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cisco.iot.ccs.model.Car;

@Repository
public interface CarDao extends JpaRepository<Car, Long> {
	
	Page<Car> findByMakeIn(Set<String> makes, Pageable pageable);

	Page<Car> findByMake(String make, Pageable pageable);

}
