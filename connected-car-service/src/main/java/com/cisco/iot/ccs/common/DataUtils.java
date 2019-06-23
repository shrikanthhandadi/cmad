package com.cisco.iot.ccs.common;

import java.util.List;

import com.cisco.iot.ccs.model.Page;

public class DataUtils {

	public static <T> Page<T> toPage(org.springframework.data.domain.Page<?> entityPage, Class<T> modelClazz) {
		List<T> data = BeanUtils.copyValues(entityPage.getContent(), modelClazz);
		Page<T> page = new Page<>();
		page.setData(data);
		page.setPageNumber(entityPage.getNumber());
		page.setPageSize(entityPage.getSize());
		page.setTotalElements(entityPage.getTotalElements());
		page.setTotalPages(entityPage.getTotalPages());
		return page;
	}
}
