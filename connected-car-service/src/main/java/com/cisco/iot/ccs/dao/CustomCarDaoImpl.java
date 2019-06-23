package com.cisco.iot.ccs.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import com.cisco.iot.ccs.entity.CarEntity;

@Component
public class CustomCarDaoImpl implements CustomCarDao {

	@Autowired
	private EntityManager em;

	@Override
	public Page<CarEntity> findAll(int pageSize, int pageNumber, String[] fields, boolean groupBy) {
		CriteriaQuery<CarEntity> selectCriteriaQuery = buildCriteriaQuery(CarEntity.class, fields, groupBy, null);
		TypedQuery<CarEntity> typedQuery = em.createQuery(selectCriteriaQuery);
		typedQuery.setFirstResult(pageNumber * pageSize);
		typedQuery.setMaxResults(pageSize);
		List<CarEntity> data = typedQuery.getResultList();

		// total count
		// CriteriaQuery<Long> countCriteriaQuery = buildCriteriaQuery(Long.class,
		// fields, groupBy, null);
		// Long totalRows = em.createQuery(countCriteriaQuery).getSingleResult();

		// total count
		// CriteriaQuery<Long> countCriteriaQuery = buildCriteriaQuery(Long.class,
		// fields, groupBy, make);
		CriteriaBuilder qb = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = qb.createQuery(Long.class);
		cq.select(qb.count(cq.from(CarEntity.class)));
		// cq.where(/*your stuff*/);
		Long totalRows = em.createQuery(cq).getSingleResult();

		return new PageImpl<>(data, PageRequest.of(pageNumber, pageSize), totalRows);
	}

	@Override
	public Page<CarEntity> findAll(String make, int pageSize, int pageNumber, String[] fields, boolean groupBy) {
		CriteriaQuery<CarEntity> selectCriteriaQuery = buildCriteriaQuery(CarEntity.class, fields, groupBy, make);
		TypedQuery<CarEntity> typedQuery = em.createQuery(selectCriteriaQuery);
		typedQuery.setFirstResult(pageNumber * pageSize);
		typedQuery.setMaxResults(pageSize);
		List<CarEntity> data = typedQuery.getResultList();

		// total count
		// CriteriaQuery<Long> countCriteriaQuery = buildCriteriaQuery(Long.class,
		// fields, groupBy, make);
		CriteriaBuilder qb = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = qb.createQuery(Long.class);
		cq.select(qb.count(cq.from(CarEntity.class)));
		// cq.where(/*your stuff*/);
		Long totalRows = em.createQuery(cq).getSingleResult();

		return new PageImpl<>(data, PageRequest.of(pageNumber, pageSize), totalRows);
	}

	private <T> CriteriaQuery<T> buildCriteriaQuery(Class<T> resultClass, String[] fields, boolean groupBy,
			String make) {
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(resultClass);
		Root<CarEntity> queryRoot = criteriaQuery.from(CarEntity.class);
		criteriaQuery.multiselect(toMuliselect(queryRoot, fields));
		if (groupBy) {
			criteriaQuery.groupBy(toGroupBy(queryRoot, fields));
		}
		if (make != null) {
			criteriaQuery.where(criteriaBuilder.equal(queryRoot.get("make"), make));
		}
		criteriaQuery.orderBy(criteriaBuilder.desc(queryRoot.get(fields[0])));
		return criteriaQuery;
	}

	private List<Selection<?>> toMuliselect(Root<CarEntity> root, String[] fields) {
		List<Selection<?>> multiselect = new ArrayList<Selection<?>>();
		for (int i = 0; i < fields.length; i++) {
			multiselect.add(root.get(fields[i]));
		}
		return multiselect;
	}

	private List<Expression<?>> toGroupBy(Root<CarEntity> root, String[] fields) {
		List<Expression<?>> multiselect = new ArrayList<>();
		for (int i = 0; i < fields.length; i++) {
			multiselect.add(root.get(fields[i]));
		}
		return multiselect;
	}

}
