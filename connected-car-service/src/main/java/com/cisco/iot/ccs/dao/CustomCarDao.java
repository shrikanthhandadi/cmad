package com.cisco.iot.ccs.dao;

import org.springframework.data.domain.Page;

import com.cisco.iot.ccs.entity.CarEntity;

public interface CustomCarDao {

	Page<CarEntity> findAll(int pageSize, int pageNumber, String[] fields, boolean groupBy);

	Page<CarEntity> findAll(String make, int pageSize, int pageNumber, String[] fields, boolean groupBy);

}
