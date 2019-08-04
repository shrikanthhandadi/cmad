package com.cisco.iot.ccs.service;

import com.cisco.iot.ccs.model.CustomerCar;
import com.cisco.iot.ccs.model.Page;

public interface CustomerCarService {

	CustomerCar create(CustomerCar event);

	CustomerCar get(Long id);

	Page<CustomerCar> get(int pageSize, int pageNumber);

}
