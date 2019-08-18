package com.cisco.iot.ccs.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cisco.iot.ccs.common.BeanUtils;
import com.cisco.iot.ccs.common.DataUtils;
import com.cisco.iot.ccs.exception.NotFoundException;
import com.cisco.iot.ccs.model.Page;
import com.cisco.iot.ccs.model.User;
import com.cisco.iot.ccs.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public User create(User user) {
		String encodedPass = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPass);
		return userRepository.save(user);
	}

	@Override
	public User get(Long id) {
		Optional<User> optional = userRepository.findById(id);
		if (!optional.isPresent()) {
			throw new NotFoundException("Unable to find user for id " + id);
		}
		return BeanUtils.copy(optional.get(), User.class);
	}

	@Override
	public Page<User> get(int pageSize, int pageNumber) {
		org.springframework.data.domain.Page<User> entityPage = null;
		entityPage = userRepository.findAll(PageRequest.of(pageNumber, pageSize));
		Page<User> page = DataUtils.toPageModel(entityPage, User.class);
		return page;
	}

	@Override
	public User get(String username) {
		return userRepository.findByUsername(username).orElseThrow(() -> {
			throw new NotFoundException("Unable to find user for username " + username);
		});
	}

	@Override
	public boolean delete(Long id) {
		userRepository.deleteById(id);
		return true;
	}

	@Override
	public User update(Long id, User user) {
		userRepository.findById(id).orElseThrow(() -> {
			throw new NotFoundException("Unable to find user for id " + id);
		});
		user.setId(id);
		return userRepository.save(user);
	}

}
