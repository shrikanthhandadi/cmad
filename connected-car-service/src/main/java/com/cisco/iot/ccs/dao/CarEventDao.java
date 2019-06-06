package com.cisco.iot.ccs.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cisco.iot.ccs.entity.CarEventEntity;

@Repository
public interface CarEventDao extends CrudRepository<CarEventEntity, Long> {

}
