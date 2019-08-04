package com.cisco.iot.ccs.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.cisco.iot.ccs.common.BeanUtils;
import com.cisco.iot.ccs.common.DataUtils;
import com.cisco.iot.ccs.dao.CustomerCarDao;
import com.cisco.iot.ccs.exception.NotFoundException;
import com.cisco.iot.ccs.model.CustomerCar;
import com.cisco.iot.ccs.model.Page;

@Service
public class CustomerCarServiceImpl implements CustomerCarService {

	@Autowired
	private CustomerCarDao customerCarDao;

	@Override
	public CustomerCar create(CustomerCar event) {
		CustomerCar entity = BeanUtils.copy(event, CustomerCar.class);
		entity = customerCarDao.save(entity);
		return BeanUtils.copy(entity, CustomerCar.class);
	}

	@Override
	public CustomerCar get(Long id) {
		Optional<CustomerCar> optional = customerCarDao.findById(id);
		if (!optional.isPresent()) {
			throw new NotFoundException("Unable to find car for id " + id);
		}
		return BeanUtils.copy(optional.get(), CustomerCar.class);
	}

	@Override
	public Page<CustomerCar> get(int pageSize, int pageNumber) {
		org.springframework.data.domain.Page<CustomerCar> entityPage = null;
		entityPage = customerCarDao.findAll(PageRequest.of(pageNumber, pageSize));
		Page<CustomerCar> page = DataUtils.toPageModel(entityPage, CustomerCar.class);
		return page;
	}

}
