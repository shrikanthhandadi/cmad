package com.cisco.iot.ccs.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cisco.iot.ccs.common.BeanUtils;
import com.cisco.iot.ccs.dao.CarEventDao;
import com.cisco.iot.ccs.entity.CarEventEntity;
import com.cisco.iot.ccs.model.CarEvent;
import com.cisco.iot.ccs.model.Severity;

@Service
public class CarEventServiceImpl implements CarEventService {

	@Autowired
	private CarEventDao carEventDao;

	@Override
	public CarEvent create(CarEvent carEvent) {
		CarEventEntity carEventEntity = BeanUtils.copy(carEvent, CarEventEntity.class);
		carEventEntity = carEventDao.save(carEventEntity);
		return BeanUtils.copy(carEventEntity, CarEvent.class);
	}

	@Override
	public Map<Severity, Long> getStats() {
		return null;
	}

	@Override
	public List<CarEvent> get(String make, boolean groupBy, String[] fields) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CarEvent> get(boolean groupBy, String[] fields, int pageNumber, int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

}
