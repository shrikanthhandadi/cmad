package com.cisco.iot.ccs.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cisco.iot.ccs.model.CustomerCar;

public interface CustomerCarDao extends JpaRepository<CustomerCar, Long> {

}
