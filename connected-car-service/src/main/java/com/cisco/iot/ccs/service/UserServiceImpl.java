package com.cisco.iot.ccs.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.cisco.iot.ccs.common.BeanUtils;
import com.cisco.iot.ccs.common.DataUtils;
import com.cisco.iot.ccs.dao.UserDao;
import com.cisco.iot.ccs.exception.NotFoundException;
import com.cisco.iot.ccs.model.Page;
import com.cisco.iot.ccs.model.User;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	@Override
	public User create(User user) {
		User entity = BeanUtils.copy(user, User.class);
		entity = userDao.save(entity);
		return BeanUtils.copy(entity, User.class);
	}

	@Override
	public User get(Long id) {
		Optional<User> optional = userDao.findById(id);
		if (!optional.isPresent()) {
			throw new NotFoundException("Unable to find user for id " + id);
		}
		return BeanUtils.copy(optional.get(), User.class);
	}

	@Override
	public Page<User> get(int pageSize, int pageNumber) {
		org.springframework.data.domain.Page<User> entityPage = null;
		entityPage = userDao.findAll(PageRequest.of(pageNumber, pageSize));
		Page<User> page = DataUtils.toPageModel(entityPage, User.class);
		return page;
	}

}
