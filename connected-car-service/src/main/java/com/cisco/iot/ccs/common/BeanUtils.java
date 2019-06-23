package com.cisco.iot.ccs.common;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.cisco.iot.ccs.model.Event;
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

	public static String[] getFieldNames(Class<?> clazz) {
		return Arrays.asList(Event.class.getFields()).stream().map(Field::getName).collect(Collectors.toList())
				.toArray(new String[] {});
	}

	public static String getFieldNamesString(Class<?> clazz) {
		String[] fields = getFieldNames(clazz);
		return Arrays.asList(fields).stream().collect(Collectors.joining(","));
	}
}
