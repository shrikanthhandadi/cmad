package com.cisco.iot.ccs.model;

import java.time.Year;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.cisco.iot.ccs.model.Car;

/**
 * {@link Car} of the customer which will send events.
 * 
 * @author shandadi
 *
 */
@Entity
public class CustomerCar {

	@Id
	@GeneratedValue
	private Long id;

	@OneToOne
	private Car car;

	@Column(columnDefinition = "smallint")
	@Convert(converter = YearAttributeConverter.class)
	private Year year;

	private String regNum;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
	}

	public Year getYear() {
		return year;
	}

	public void setYear(Year year) {
		this.year = year;
	}

	public String getRegNum() {
		return regNum;
	}

	public void setRegNum(String regNum) {
		this.regNum = regNum;
	}

}
