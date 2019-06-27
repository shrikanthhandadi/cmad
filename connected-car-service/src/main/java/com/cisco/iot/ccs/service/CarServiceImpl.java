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
		if (!optional.isPresent()) {
			throw new DataNotFoundException("Unable to find car for id " + id);
		}
		return BeanUtils.copy(optional.get(), Car.class);
	}

	@Override
	public Page<Car> get(String make, String[] fields, boolean distinct, int pageSize, int pageNumber) {
		org.springframework.data.domain.Page<CarEntity> entityPage = null;
		if (fields == null && make == null) {
			entityPage = carDao.findAll(pageSize, pageNumber);
		} else if (fields == null && make != null) {
			entityPage = carDao.findAll(make, pageSize, pageNumber);
		} else {
			entityPage = carDao.findAll(make, fields, distinct, pageSize, pageNumber);
		}
		Page<Car> page = DataUtils.toPageModel(entityPage, Car.class);
		return page;
	}

}
