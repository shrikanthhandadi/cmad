package com.cisco.iot.ccs.service;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.cisco.iot.ccs.common.BeanUtils;
import com.cisco.iot.ccs.common.DataUtils;
import com.cisco.iot.ccs.dao.CarDao;
import com.cisco.iot.ccs.exception.NotFoundException;
import com.cisco.iot.ccs.model.Car;
import com.cisco.iot.ccs.model.Page;

@Service
public class CarServiceImpl implements CarService {

	@Autowired
	private CarDao carDao;

	@Override
	public Car create(Car event) {
		Car entity = BeanUtils.copy(event, Car.class);
		entity = carDao.save(entity);
		return BeanUtils.copy(entity, Car.class);
	}

	@Override
	public Car get(Long id) {
		Optional<Car> optional = carDao.findById(id);
		if (!optional.isPresent()) {
			throw new NotFoundException("Unable to find car for id " + id);
		}
		return BeanUtils.copy(optional.get(), Car.class);
	}

	@Override
	public Page<Car> get(Set<String> makes, int pageSize, int pageNumber) {
		org.springframework.data.domain.Page<Car> entityPage = null;
		entityPage = carDao.findByMakeIn(makes, PageRequest.of(pageNumber, pageSize));
		Page<Car> page = DataUtils.toPageModel(entityPage, Car.class);
		return page;
	}

	@Override
	public Page<Car> get(String make, int pageSize, int pageNumber) {
		org.springframework.data.domain.Page<Car> entityPage = null;
		entityPage = carDao.findByMake(make, PageRequest.of(pageNumber, pageSize));
		Page<Car> page = DataUtils.toPageModel(entityPage, Car.class);
		return page;
	}

	@Override
	public Page<Car> get(int pageSize, int pageNumber) {
		org.springframework.data.domain.Page<Car> entityPage = null;
		entityPage = carDao.findAll(PageRequest.of(pageNumber, pageSize));
		Page<Car> page = DataUtils.toPageModel(entityPage, Car.class);
		return page;
	}

}
