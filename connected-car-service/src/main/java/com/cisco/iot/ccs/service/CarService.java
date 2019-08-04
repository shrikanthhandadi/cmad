package com.cisco.iot.ccs.service;

import com.cisco.iot.ccs.model.Car;
import com.cisco.iot.ccs.model.Page;

public interface CarService {

	Car create(Car event);

	Car get(Long id);
	
	Page<Car> get(int pageSize, int pageNumber);
}
