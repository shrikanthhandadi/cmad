package com.cisco.iot.ccs.dao;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Component;

import com.cisco.iot.ccs.common.DataUtils;
import com.cisco.iot.ccs.entity.CarEntity;

@Component
public class CarDaoImpl {

	@Autowired
	private CarDao carDao;

	@Autowired
	private EntityManager em;

	public Page<CarEntity> findAll(int pageSize, int pageNumber) {
		PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, Sort.by(Direction.DESC, "id"));
		return carDao.findAll(pageRequest);
	}

	public Page<CarEntity> findAll(String make, int pageSize, int pageNumber) {
		PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, Sort.by(Direction.DESC, "id"));
		return carDao.findByMake(make, pageRequest);
	}

	@SuppressWarnings("unchecked")
	public Page<CarEntity> findAll(String make, String[] fields, boolean distinct, int pageSize, int pageNumber) {
		String selectSql = buildSelectQuery(fields, make, distinct);
		Query query = em.createQuery(selectSql);
		query.setFirstResult(pageSize * pageNumber);
		query.setMaxResults(pageSize);
		List<CarEntity> data = null;
		if (fields.length == 1) {
			List<Object> resultList = query.getResultList();
			data = DataUtils.map((List<Object>) resultList, fields[0], CarEntity.class);
		} else {
			List<Object[]> resultList = query.getResultList();
			data = DataUtils.map(resultList, Arrays.asList(fields), CarEntity.class);
		}
		String countSql = buildCountQuery(fields, make, distinct);
		BigInteger totalRows = (BigInteger) em.createNativeQuery(countSql).getSingleResult();

		return new PageImpl<>(data, PageRequest.of(pageNumber, pageSize), totalRows.longValue());
	}

	private String buildSelectQuery(String[] fields, String make, boolean distinct) {
		String table = CarEntity.class.getCanonicalName() + " e";
		String fieldStr = Arrays.asList(fields).stream().map(DataUtils::toSnakeCase)
				.collect(Collectors.joining(",", "e.", ""));
		String query = "select";
		if (distinct) {
			query = query + " distinct";
		}
		query = query + " " + fieldStr + " from " + table;
		if (make != null) {
			query = query + " where e.make='" + make + "'";
		}
		query = query + " order by e." + DataUtils.toSnakeCase(fields[0]) + " desc";
		return query;
	}

	private String buildCountQuery(String[] fields, String make, boolean distinct) {
		String fieldStr = Arrays.asList(fields).stream().map(DataUtils::toSnakeCase).collect(Collectors.joining(","));
		String query = "SELECT COUNT(1) FROM ( SELECT";
		if (distinct) {
			query = query + " distinct";
		}
		query = query + " " + fieldStr + " from car";
		if (make != null) {
			query = query + " where make='" + make + "'";
		}
		query = query + " order by " + DataUtils.toSnakeCase(fields[0]) + ") t";
		return query;
	}

}
