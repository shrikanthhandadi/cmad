package com.cisco.iot.ccs.dao;

import org.springframework.data.domain.Page;

import com.cisco.iot.ccs.entity.EventEntity;

public interface CustomEventDao {

	Page<EventEntity> findAll(int pageSize, int pageNumber);
	
	Page<EventEntity> findAll(String make, int pageSize, int pageNumber);

	Page<EventEntity> findAll(String make, String model, int pageSize, int pageNumber);
}
