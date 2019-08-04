package com.cisco.iot.ccs.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cisco.iot.ccs.model.User;

@Repository
public interface UserDao extends JpaRepository<User, Long> {

	Optional<User> findByUsername(String username);

}
