package com.cisco.iot.ccs.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cisco.iot.ccs.entity.Car;

@Repository
public interface CarDao extends CrudRepository<Car, Long> {

}
