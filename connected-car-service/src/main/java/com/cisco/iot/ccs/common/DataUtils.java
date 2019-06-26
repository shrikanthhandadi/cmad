package com.cisco.iot.ccs.common;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cisco.iot.ccs.model.Page;

public class DataUtils {

	private static final Logger log = LoggerFactory.getLogger(DataUtils.class);

	public static <T> Page<T> toPageModel(org.springframework.data.domain.Page<?> entityPage, Class<T> modelClazz) {
		List<T> data = BeanUtils.copyValues(entityPage.getContent(), modelClazz);
		Page<T> page = new Page<>();
		page.setData(data);
		page.setPageNumber(entityPage.getNumber());
		page.setPageSize(entityPage.getSize());
		page.setTotalElements(entityPage.getTotalElements());
		page.setTotalPages(entityPage.getTotalPages());
		return page;
	}

	public static String toSnakeCase(String camelCase) {
		return camelCase.replaceAll("(.)(\\p{Upper})", "$1_$2").toLowerCase();
	}

	public static String toCommaSeparatedString(String[] values) {
		return Arrays.asList(values).stream().map(DataUtils::toSnakeCase).collect(Collectors.joining(","));
	}

	public static <T> List<T> map(List<Object[]> objectArrays, List<String> fields, Class<T> clazz) {
		List<T> data = new ArrayList<>();
		int size = fields.size();
		try {
			for (Object[] objectArr : objectArrays) {
				T entity = clazz.getDeclaredConstructor().newInstance();
				for (int i = 0; i < objectArr.length; i++) {
					org.apache.commons.beanutils.BeanUtils.setProperty(entity, fields.get(i % size), objectArr[i]);
				}
				data.add(entity);
			}
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			log.error("Exception while mapping data: {} to fields {}", objectArrays, fields);
			throw new RuntimeException("Exception while mapping data", e);
		}
		return data;
	}

	public static <T> List<T> map(List<Object> objects, String field, Class<T> clazz) {
		List<T> data = new ArrayList<>();
		for (Object object : objects) {
			try {
				T entity = clazz.getDeclaredConstructor().newInstance();
				org.apache.commons.beanutils.BeanUtils.setProperty(entity, field, object);
				data.add(entity);
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException | SecurityException e) {
				log.error("Exception while mapping data: {} to field {}", objects, field);
				throw new RuntimeException("Exception while mapping data", e);
			}
		}

		return data;
	}
}
