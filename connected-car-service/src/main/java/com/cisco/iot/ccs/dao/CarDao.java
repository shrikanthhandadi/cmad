package com.cisco.iot.ccs.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cisco.iot.ccs.entity.CarEntity;

@Repository
public interface CarDao extends JpaRepository<CarEntity, Long> {

	Page<CarEntity> findAll(int pageSize, int pageNumber);

	Page<CarEntity> findAll(String make, int pageSize, int pageNumber);

	Page<CarEntity> findAll(String make, String[] fields, boolean distinct, int pageSize, int pageNumber);

	// spring data standard
	Page<CarEntity> findByMake(String make, Pageable pageable);

}
