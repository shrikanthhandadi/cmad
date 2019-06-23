package com.cisco.iot.ccs.model;

import java.time.Year;

import io.swagger.annotations.ApiModel;

@ApiModel
public class Car {

	private Long id;
	
	private String make;
	
	private String model;
	
	private Year year;

	private String regNum;

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
