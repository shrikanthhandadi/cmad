package com.cisco.iot.ccs.entity;

import java.time.Year;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "car")
public class CarEntity {

	@Id
	@GeneratedValue
	private Long id;

	private String make;

	private String model;

	@Column(name = "year", columnDefinition = "smallint")
	@Convert(converter = YearAttributeConverter.class)
	private Year year;

	private String regNum;

	public CarEntity() {
	}

	public CarEntity(String make) {
		this.make = make;
	}

	public CarEntity(String make, String model) {
		this.make = make;
		this.model = model;
	}

	public CarEntity(Long id, String make, String model, Year year, String regNum) {
		super();
		this.id = id;
		this.make = make;
		this.model = model;
		this.year = year;
		this.regNum = regNum;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMake() {
		return make;
	}

	public void setMake(String make) {
		this.make = make;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
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
