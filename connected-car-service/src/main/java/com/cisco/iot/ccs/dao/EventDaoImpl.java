package com.cisco.iot.ccs.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Component;

import com.cisco.iot.ccs.entity.EventEntity;

@Component
public class EventDaoImpl  {

	@Autowired
	private EventDao eventDao;

	public Page<EventEntity> findAll(int pageSize, int pageNumber) {
		PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, Sort.by(Direction.DESC, "id"));
		return eventDao.findAll(pageRequest);
	}

	public Page<EventEntity> findAll(String make, int pageSize, int pageNumber) {
		PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, Sort.by(Direction.DESC, "id"));
		return eventDao.findByMake(make, pageRequest);
	}

	public Page<EventEntity> findAll(String make, String model, int pageSize, int pageNumber) {
		PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, Sort.by(Direction.DESC, "id"));
		return eventDao.findByMakeAndModel(make, model, pageRequest);
	}

}
