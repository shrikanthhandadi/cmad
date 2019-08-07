package com.cisco.iot.ccs.service;

import com.cisco.iot.ccs.model.Page;
import com.cisco.iot.ccs.model.User;

public interface UserService {

	User create(User user);

	User get(Long id);

	User get(String username);

	Page<User> get(int pageSize, int pageNumber);

	boolean delete(Long id);

	User update(Long id, User user);
}
