package com.cisco.iot.ccs.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cisco.iot.ccs.entity.EventEntity;

@Repository
public interface EventDao extends JpaRepository<EventEntity, Long> {

	Page<EventEntity> findByMake(String make, Pageable pageable);

	Page<EventEntity> findByMakeAndModel(String make, String model, Pageable pageable);
	
	Page<EventEntity> findAll(int pageSize, int pageNumber);
	
	Page<EventEntity> findAll(String make, int pageSize, int pageNumber);
	
	Page<EventEntity> findAll(String make, String model, int pageSize, int pageNumber);
}
