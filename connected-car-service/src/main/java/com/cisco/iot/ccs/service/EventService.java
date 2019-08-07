package com.cisco.iot.ccs.service;

import java.util.List;
import java.util.Set;

import com.cisco.iot.ccs.model.Event;
import com.cisco.iot.ccs.model.Page;
import com.cisco.iot.ccs.model.Stat;

public interface EventService {

	Event create(Event event);

	Page<Event> get(Set<String> makes, int pageSize, int pageNumber);

	Page<Event> get(String make, int pageSize, int pageNumber);

	List<Stat> getStats(String make);
	
	List<Stat> getStats(Set<String> makes);

	List<Stat> getStats(String make, String model);

	Page<Event> get(String make, String model, int pageSize, int pageNum);

}
