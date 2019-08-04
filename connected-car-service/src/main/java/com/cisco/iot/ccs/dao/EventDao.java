package com.cisco.iot.ccs.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.cisco.iot.ccs.model.Event;
import com.cisco.iot.ccs.model.Severity;

@Repository
public interface EventDao extends MongoRepository<Event, Long> {

	Page<Event> findAll(Pageable pageable);

	Page<Event> findByMake(String make, Pageable pageable);

	Page<Event> findByMakeAndModel(String make, String model, Pageable pageable);

	// @Query(value = "select severity, count(1) count from event group by
	// severity", nativeQuery = true)
	//@Query("([{'$match' : {'make' : 'BMW'}}, { '$group' : {'_id' : { 'make' : '$make'}, 'COUNT(*)' : {'$sum' : NumberInt(1)}}}, {  '$project' : { 'make' : '$_id.make', 'COUNT(*)' : '$COUNT(*)', '_id' : NumberInt(0)} }],  { 'allowDiskUse' : true });")
	@Query("{ 'username' : ?0 }")
	List<Stat> getStats();

	@Query("{ 'username' : ?0 }")
	List<Stat> getStats(String make);

	@Query("{ 'username' : ?0 }")
	List<Stat> getStats(String make, String model);

	interface Stat {
		Severity getSeverity();

		int getCount();
	}
}
