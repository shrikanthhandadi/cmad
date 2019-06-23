package com.cisco.iot.ccs.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cisco.iot.ccs.common.BeanUtils;
import com.cisco.iot.ccs.common.DataUtils;
import com.cisco.iot.ccs.dao.CarDao;
import com.cisco.iot.ccs.entity.CarEntity;
import com.cisco.iot.ccs.exception.DataNotFoundException;
import com.cisco.iot.ccs.model.Car;
import com.cisco.iot.ccs.model.Page;

@Service
public class CarServiceImpl implements CarService {

	@Autowired
	private CarDao carDao;

	@Override
	public Car create(Car event) {
		CarEntity entity = BeanUtils.copy(event, CarEntity.class);
		entity = carDao.save(entity);
		return BeanUtils.copy(entity, Car.class);
	}

	@Override
	public Car get(Long id) {
		Optional<CarEntity> optional = carDao.findById(id);
		if (optional.isEmpty()) {
			throw new DataNotFoundException("Unable to find car for id " + id);
		}
		return BeanUtils.copy(optional.get(), Car.class);
	}

	@Override
	public Page<Car> get(int pageSize, int pageNumber, String[] fields, boolean groupBy) {
		org.springframework.data.domain.Page<CarEntity> entityPage = carDao.findAll(pageSize, pageNumber, fields, groupBy);
		Page<Car> page = DataUtils.toPage(entityPage, Car.class);
		return page;
	}

	@Override
	public Page<Car> get(String make, int pageSize, int pageNumber, String[] fields, boolean groupBy) {
		org.springframework.data.domain.Page<CarEntity> entityPage = carDao.findAll(make, pageSize, pageNumber, fields,
				groupBy);
		Page<Car> page = DataUtils.toPage(entityPage, Car.class);
		return page;
	}

}
