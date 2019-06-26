package com.cisco.iot.ccs.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cisco.iot.ccs.common.BeanUtils;
import com.cisco.iot.ccs.common.DataUtils;
import com.cisco.iot.ccs.dao.EventDao;
import com.cisco.iot.ccs.entity.EventEntity;
import com.cisco.iot.ccs.model.Event;
import com.cisco.iot.ccs.model.Page;
import com.cisco.iot.ccs.model.Severity;

@Service
public class EventServiceImpl implements EventService {

	@Autowired
	private EventDao eventDao;

	@Override
	public Event create(Event carEvent) {
		EventEntity eventEntity = BeanUtils.copy(carEvent, EventEntity.class);
		eventEntity = eventDao.save(eventEntity);
		return BeanUtils.copy(eventEntity, Event.class);
	}

	@Override
	public Page<Event> get(int pageSize, int pageNumber) {
		org.springframework.data.domain.Page<EventEntity> entityPage = eventDao.findAll(pageSize, pageNumber);
		Page<Event> page = DataUtils.toPageModel(entityPage, Event.class);
		return page;
	}

	@Override
	public Page<Event> get(String make, int pageSize, int pageNumber) {
		org.springframework.data.domain.Page<EventEntity> entityPage = eventDao.findAll(make, pageSize, pageNumber);
		Page<Event> page = DataUtils.toPageModel(entityPage, Event.class);
		return page;
	}

	@Override
	public Page<Event> get(String make, String model, int pageSize, int pageNumber) {
		org.springframework.data.domain.Page<EventEntity> entityPage = eventDao.findAll(make, model, pageSize,
				pageNumber);
		Page<Event> page = DataUtils.toPageModel(entityPage, Event.class);
		return page;
	}

	@Override
	public Map<Severity, Long> getStats(String make, String model) {
		// TODO Auto-generated method stub
		return null;
	}

}
