package com.cisco.iot.ccs.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.cisco.iot.ccs.common.BeanUtils;
import com.cisco.iot.ccs.common.DataUtils;
import com.cisco.iot.ccs.model.Event;
import com.cisco.iot.ccs.model.Page;
import com.cisco.iot.ccs.model.Stat;
import com.cisco.iot.ccs.repository.EventRepository;

@Service
public class EventServiceImpl implements EventService {

	@Autowired
	private EventRepository eventRepository;

	@Override
	public Event create(Event carEvent) {
		Event event = BeanUtils.copy(carEvent, Event.class);
		event = eventRepository.save(event);
		return BeanUtils.copy(event, Event.class);
	}

	@Override
	public Page<Event> get(String make, int pageSize, int pageNumber) {
		org.springframework.data.domain.Page<Event> entityPage = eventRepository.findByMake(make,
				PageRequest.of(pageNumber, pageSize));
		Page<Event> page = DataUtils.toPageModel(entityPage, Event.class);
		return page;
	}

	@Override
	public List<Stat> getStats(String make) {
		List<com.cisco.iot.ccs.repository.EventRepository.Stat> stats = eventRepository.getStats(make);
		List<Stat> statCounts = BeanUtils.copyValues(stats, Stat.class);
		return statCounts;
	}

	@Override
	public List<Stat> getStats(String make, String model) {
		List<com.cisco.iot.ccs.repository.EventRepository.Stat> stats = eventRepository.getStats(make, model);
		List<Stat> statCounts = BeanUtils.copyValues(stats, Stat.class);
		return statCounts;
	}

	@Override
	public Page<Event> get(Set<String> makes, int pageSize, int pageNumber) {
		org.springframework.data.domain.Page<Event> events = eventRepository.findByMakeIn(makes,
				PageRequest.of(pageNumber, pageSize));
		Page<Event> page = DataUtils.toPageModel(events, Event.class);
		return page;
	}

	@Override
	public List<Stat> getStats(Set<String> makes) {
		List<com.cisco.iot.ccs.repository.EventRepository.Stat> stats = eventRepository.getStats(makes);
		List<Stat> statCounts = BeanUtils.copyValues(stats, Stat.class);
		return statCounts;
	}

	@Override
	public Page<Event> get(String make, String model, int pageSize, int pageNum) {
		org.springframework.data.domain.Page<Event> entityPage = eventRepository.findByMakeAndModel(make, model,
				PageRequest.of(pageNum, pageSize));
		Page<Event> page = DataUtils.toPageModel(entityPage, Event.class);
		return page;
	}

}
