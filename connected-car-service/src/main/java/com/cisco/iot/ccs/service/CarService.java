package com.cisco.iot.ccs.service;

import java.util.Set;

import com.cisco.iot.ccs.model.Car;
import com.cisco.iot.ccs.model.Page;

public interface CarService {

	Car create(Car event);

	Car get(Long id);

	Page<Car> get(String make, int pageSize, int pageNumber);

	Page<Car> get(Set<String> makes, int pageSize, int pageNumber);

}
