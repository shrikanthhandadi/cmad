package com.cisco.iot.ccs.common;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class BeanUtils {

	private static final ObjectMapper MAPPER = Jackson2ObjectMapperBuilder.json()
			.serializationInclusion(JsonInclude.Include.NON_NULL)
			.featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS).modules(new JavaTimeModule()).build();

	public static <T> List<T> copyValues(List<?> objects, Class<T> targetClass) {
		List<T> targets = objects.parallelStream().map(e -> copy(e, targetClass)).collect(Collectors.toList());
		return targets;
	}

	public static <T> T copy(Object entity, Class<T> targetClass) {
		return MAPPER.convertValue(entity, targetClass);
	}
}
