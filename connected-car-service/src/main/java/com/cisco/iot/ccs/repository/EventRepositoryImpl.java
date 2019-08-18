package com.cisco.iot.ccs.repository;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.project;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.sort;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Component;

import com.cisco.iot.ccs.repository.EventRepository.Stat;

@Component
public class EventRepositoryImpl {

	@Autowired
	private MongoOperations operations;

	public List<Stat> getStats(String make) {
		Criteria criteria = Criteria.where("make").is(make);
		MatchOperation matchStage = Aggregation.match(criteria);
		Aggregation aggregation = newAggregation(matchStage, group("severity").count().as("count"),
				sort(Direction.DESC, "severity"), project("count").and("_id").as("severity"));
		AggregationResults<Stat> result = operations.aggregate(aggregation, "event", Stat.class);
		return result.getMappedResults();
	}

	public List<Stat> getStats(Set<String> makes) {
		Criteria criteria = Criteria.where("make").in(makes);
		MatchOperation matchStage = Aggregation.match(criteria);
		Aggregation aggregation = newAggregation(matchStage, group("severity").count().as("count"),
				sort(Direction.DESC, "severity"), project("count").and("_id").as("severity"));
		AggregationResults<Stat> result = operations.aggregate(aggregation, "event", Stat.class);
		return result.getMappedResults();
	}

	public List<Stat> getStats(String make, String model) {
		Criteria criteria = Criteria.where("make").is(make).and("model").is(model);
		MatchOperation matchStage = Aggregation.match(criteria);
		Aggregation aggregation = newAggregation(matchStage, group("severity").count().as("count"),
				sort(Direction.DESC, "severity"), project("count").and("_id").as("severity"));
		AggregationResults<Stat> result = operations.aggregate(aggregation, "event", Stat.class);
		return result.getMappedResults();
	}

}
