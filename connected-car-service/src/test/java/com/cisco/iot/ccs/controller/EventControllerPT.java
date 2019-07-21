package com.cisco.iot.ccs.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.cisco.iot.ccs.model.Car;
import com.cisco.iot.ccs.model.Event;
import com.cisco.iot.ccs.model.Page;
import com.cisco.iot.ccs.model.Severity;

public class EventControllerPT {

	private static final Logger log = LoggerFactory.getLogger(EventControllerPT.class);

	RestTemplate restTemplate = new RestTemplate();

	private static final int port = 9090;

	final String baseUrl = "http://localhost:" + port + "/ccs";

	@Test
	public void testExecute() throws IOException, InterruptedException {
		executeCreate("src/test/resources/event/event.csv", 5, 1, Double.valueOf(1 * 60 * 3).intValue());
	}

	private void executeCreate(String location, int concurrency, int rampupSeconds, int loop)
			throws IOException, InterruptedException {
		List<Event> data = buildEvents(concurrency * loop);
		List<Callable<Long>> createPayloads = buildCreatePayloads(data);
		List<Long> ids = executePerfTest(createPayloads, concurrency, rampupSeconds, loop);
		log.info("Created data ids: {}", ids);
	}

	private <T> List<T> executePerfTest(List<Callable<T>> payloads, int concurrency, int rampupSeconds, int loop)
			throws InterruptedException {
		List<T> results = new ArrayList<>();
		ExecutorService execServ = Executors.newFixedThreadPool(concurrency);
		long intervalMillis = TimeUnit.SECONDS.toMillis(rampupSeconds) / concurrency;
		for (int i = 0; i < loop; i++) {
			List<T> res = executePerf(execServ, concurrency, intervalMillis, i, payloads);
			results.addAll(res);
		}
		execServ.shutdown();
		execServ.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
		return results;
	}

	private <T> List<T> executePerf(ExecutorService executorService, int concurrency, long intervalMillis, int loop,
			List<Callable<T>> payloads) throws InterruptedException {
		List<Future<T>> futures = new ArrayList<>();
		List<T> results = new ArrayList<T>();
		int payloadSize = payloads.size();
		int i = loop * concurrency;
		while (concurrency > 0) {
			if (i >= payloadSize) {
				i = 0;
			}
			Future<T> future = executorService.submit(payloads.get(i));
			futures.add(future);
			TimeUnit.MILLISECONDS.sleep(intervalMillis);
			concurrency--;
			i++;
		}
		for (Future<T> future : futures) {
			try {
				results.add(future.get());
			} catch (ExecutionException ex) {
				log.error("Exception while executing call " + ex.getCause().getMessage(), ex.getCause());
				throw new RuntimeException("Exception while executing call " + ex.getCause().getMessage(),
						ex.getCause());
			}
		}
		return results;
	}

	private List<Callable<Long>> buildCreatePayloads(List<Event> events) {
		List<Callable<Long>> payloads = new ArrayList<>();
		for (Event event : events) {
			Callable<Long> createRequest = buildCreateRequest(event);
			payloads.add(createRequest);
		}
		return payloads;
	}

	private Callable<Long> buildCreateRequest(Event event) {
		return new Callable<Long>() {
			@Override
			public Long call() throws Exception {
				ResponseEntity<Long> response = restTemplate.postForEntity(baseUrl + "/events", event, Long.class);
				return response.getBody();
			}
		};
	}

	private List<Event> buildEvents(int maxSize) throws IOException {
		List<Event> events = new ArrayList<>();
		ResponseEntity<Page<Car>> response = restTemplate.exchange(baseUrl + "/cars?pageSize=1000&pageNum=0",
				HttpMethod.GET, null, new ParameterizedTypeReference<Page<Car>>() {
				});

		List<Car> cars = response.getBody().getData();
		int size = cars.size();
		if (size == 0) {
			log.error("No cars registered yet!");
			return events;
		}
		Random rand = new Random();
		List<Severity> svs = Arrays.asList(Severity.values());
		while (maxSize > 0) {
			for(Car car : cars) {
				Event event = new Event();
				event.setCarId(car.getId());
				event.setMake(car.getMake());
				event.setModel(car.getModel());
				event.setSeverity(svs.get(rand.nextInt(svs.size())));
				event.setDate(new Date());
				event.setData("{\"temp\":40}");
				events.add(event);
				maxSize--;
			}
		}
		return events;
	}

}
