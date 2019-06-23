package com.cisco.iot.ccs.service;

import java.util.Map;

import com.cisco.iot.ccs.model.Event;
import com.cisco.iot.ccs.model.Page;
import com.cisco.iot.ccs.model.Severity;

public interface EventService {

	Event create(Event event);

	Page<Event> get(int pageSize, int pageNumber);

	Page<Event> get(String make, int pageSize, int pageNumber);

	Page<Event> get(String make, String model, int pageSize, int pageNumber);

	Map<Severity, Long> getStats(String make, String model);

}
