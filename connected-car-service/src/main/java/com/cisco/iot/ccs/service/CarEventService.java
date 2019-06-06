package com.cisco.iot.ccs.service;

import java.util.List;
import java.util.Map;

import com.cisco.iot.ccs.model.CarEvent;
import com.cisco.iot.ccs.model.Severity;

public interface CarEventService {

	CarEvent create(CarEvent carEvent);

	List<CarEvent> get(boolean groupBy, String[] fields, int pageNumber, int pageSize);

	List<CarEvent> get(String make, boolean groupBy, String[] fields);

	Map<Severity, Long> getStats();

}
