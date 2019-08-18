package com.cisco.iot.ccs.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.cisco.iot.ccs.model.Event;
import com.cisco.iot.ccs.model.Severity;

@Repository
public interface EventRepository extends MongoRepository<Event, Long> {

	Page<Event> findByMakeIn(Set<String> makes, Pageable pageable);

	Page<Event> findByMake(String make, Pageable pageable);

	Page<Event> findByMakeAndModel(String make, String model, Pageable pageable);

	List<Stat> getStats(String make);

	List<Stat> getStats(Set<String> makes);

	List<Stat> getStats(String make, String model);

	public class Stat {

		private Severity severity;

		private int count;

		public Severity getSeverity() {
			return severity;
		}

		public void setSeverity(Severity severity) {
			this.severity = severity;
		}

		public int getCount() {
			return count;
		}

		public void setCount(int count) {
			this.count = count;
		}

	}
}
