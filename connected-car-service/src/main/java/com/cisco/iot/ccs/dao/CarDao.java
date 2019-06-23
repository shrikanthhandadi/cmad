package com.cisco.iot.ccs.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cisco.iot.ccs.entity.CarEntity;

@Repository
public interface CarDao extends JpaRepository<CarEntity, Long>, CustomCarDao {

}
