package com.cisco.iot.ccs.controller;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.cisco.iot.ccs.model.Car;

public class CarControllerPT {

	private static final Logger log = LoggerFactory.getLogger(CarControllerPT.class);

	RestTemplate restTemplate = new RestTemplate();

	private static final int port = 2020;

	final String baseUrl = "http://localhost:" + port + "/ccs";

	@Test
	public void testExecute() throws IOException, InterruptedException {
		executeCreate("src/test/resources/car/car.csv", 1, 2, 1);
	}

	private void executeCreate(String location, int concurrency, int rampupSeconds, int loop)
			throws IOException, InterruptedException {
		List<Car> data = read(location);
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

	private List<Callable<Long>> buildCreatePayloads(List<Car> cars) {
		List<Callable<Long>> payloads = new ArrayList<>();
		for (Car car : cars) {
			Callable<Long> createRequest = buildCreateRequest(car);
			payloads.add(createRequest);
		}
		return payloads;
	}

	private Callable<Long> buildCreateRequest(Car car) {
		return new Callable<Long>() {
			@Override
			public Long call() throws Exception {
				ResponseEntity<Long> response = restTemplate.postForEntity(baseUrl + "/cars", car, Long.class);
				return response.getBody();
			}
		};
	}

	private List<Car> read(String location) throws IOException {
		List<Car> cars = new ArrayList<>();
		File file = new File(location);
		List<String> lines = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
		for (String line : lines) {
			String[] array = line.split(",");
			Car car = new Car();
			car.setMake(array[0]);
			car.setModel(array[1]);
			car.setYear(Year.of(Short.valueOf(array[2])));
			car.setRegNum(array[3]);
			cars.add(car);
		}
		return cars;
	}

}
