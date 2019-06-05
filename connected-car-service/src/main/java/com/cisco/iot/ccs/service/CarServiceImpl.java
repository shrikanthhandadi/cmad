package com.cisco.iot.ccs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cisco.iot.ccs.common.BeanUtils;
import com.cisco.iot.ccs.dao.CarDao;
import com.cisco.iot.ccs.model.Car;

@Service
public class CarServiceImpl implements CarService {

	@Autowired
	private CarDao carDao;

	@Override
	public Car create(Car car) {
		com.cisco.iot.ccs.entity.Car carE = BeanUtils.copy(car, com.cisco.iot.ccs.entity.Car.class);
		carE = carDao.save(carE);
		return BeanUtils.copy(carE, Car.class);
	}

}
