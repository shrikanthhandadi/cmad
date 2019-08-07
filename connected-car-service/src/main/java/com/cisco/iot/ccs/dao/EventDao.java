package com.cisco.iot.ccs.dao;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cisco.iot.ccs.model.Event;
import com.cisco.iot.ccs.model.Severity;

@Repository
public interface EventDao extends JpaRepository<Event, Long> {
	
	Page<Event> findByMakeIn(Set<String> makes, Pageable pageable);

	Page<Event> findByMake(String make, Pageable pageable);

	Page<Event> findByMakeAndModel(String make, String model, Pageable pageable);

	@Query(value = "select severity, count(1) count from event where make = :make group by severity", nativeQuery = true)
	List<Stat> getStats(String make);
	
	@Query(value = "select severity, count(1) count from event where make in :makes group by severity", nativeQuery = true)
	List<Stat> getStats(Set<String> makes);

	@Query(value = "select severity, count(1) count from event where make = :make and model = :model group by severity", nativeQuery = true)
	List<Stat> getStats(String make, String model);

	interface Stat {
		Severity getSeverity();

		int getCount();
	}
}
