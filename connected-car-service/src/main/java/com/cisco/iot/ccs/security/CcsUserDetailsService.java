package com.cisco.iot.ccs.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cisco.iot.ccs.dao.UserDao;
import com.cisco.iot.ccs.exception.NotFoundException;
import com.cisco.iot.ccs.model.User;

@Service
public class CcsUserDetailsService implements UserDetailsService {

	private static final Logger log = LoggerFactory.getLogger(CcsUserDetailsService.class);

	@Autowired
	private UserDao userDao;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userDao.findByUsername(username)
				.orElseThrow(() -> new NotFoundException("User not found with username: " + username));
		log.info("User login success for user: {}, available roles: {}, assigned makes: {} ", username, user.getRoles(),
				user.getMakes());
		return UserPrincipal.create(user);
	}

	@Transactional
	public UserDetails loadUserById(Long id) {
		User user = userDao.findById(id).orElseThrow(() -> new NotFoundException("User not found for id: " + id));
		log.info("User login success for user: {}, available roles: {}, assigned makes: {} ", user.getUsername(),
				user.getRoles(), user.getMakes());
		return UserPrincipal.create(user);
	}

}