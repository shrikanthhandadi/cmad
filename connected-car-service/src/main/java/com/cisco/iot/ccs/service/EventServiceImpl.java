package com.cisco.iot.ccs.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Service;

import com.cisco.iot.ccs.common.BeanUtils;
import com.cisco.iot.ccs.dao.CarDao;
import com.cisco.iot.ccs.dao.EventDao;
import com.cisco.iot.ccs.entity.Car;
import com.cisco.iot.ccs.model.Event;

@Service
public class EventServiceImpl implements EventService {

	@Autowired
	private EventDao eventDao;

	@Autowired
	private CarDao carDao;

	@Override
	public Event create(Event event) {
		com.cisco.iot.ccs.entity.Event eventE = BeanUtils.copy(event, com.cisco.iot.ccs.entity.Event.class);
		Optional<Car> car = carDao.findById(event.getCar().getId());
		if (car.isEmpty()) {
			throw new InvalidDataAccessApiUsageException("Unable to find car for id: " + event.getCar().getId());
		}
		eventE.setCar(car.get());
		eventE = eventDao.save(eventE);
		return BeanUtils.copy(eventE, Event.class);
	}

}
