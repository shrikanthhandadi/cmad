package com.cisco.iot.ccs.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cisco.iot.ccs.entity.EventEntity;
import com.cisco.iot.ccs.entity.Severity;

@Repository
public interface EventDao extends JpaRepository<EventEntity, Long> {

	Page<EventEntity> findByMake(String make, Pageable pageable);

	Page<EventEntity> findByMakeAndModel(String make, String model, Pageable pageable);

	Page<EventEntity> findAll(int pageSize, int pageNumber);

	Page<EventEntity> findAll(String make, int pageSize, int pageNumber);

	Page<EventEntity> findAll(String make, String model, int pageSize, int pageNumber);

	@Query(value = "select severity, count(1) count from event group by severity", nativeQuery = true)
	List<Stat> getStats();

	@Query(value = "select severity, count(1) count from event where make = :make group by severity", nativeQuery = true)
	List<Stat> getStats(String make);

	@Query(value = "select severity, count(1) count from event where make = :make and model = :model group by severity", nativeQuery = true)
	List<Stat> getStats(String make, String model);

	interface Stat {
		Severity getSeverity();

		int getCount();
	}
}
