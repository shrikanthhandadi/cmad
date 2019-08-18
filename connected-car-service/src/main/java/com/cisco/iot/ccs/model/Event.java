package com.cisco.iot.ccs.model;

import java.util.Date;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document
public class Event {

	@Field("_id")
	private String id;

	private String make;

	private String model;

	private Long carId;

	@Enumerated(EnumType.STRING)
	private Severity severity;

	private Date date;

	private String data;

	public String getId() {
		return id;
	}

	public void setId(String id) {
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

	public Long getCarId() {
		return carId;
	}

	public void setCarId(Long carId) {
		this.carId = carId;
	}

	public Severity getSeverity() {
		return severity;
	}

	public void setSeverity(Severity severity) {
		this.severity = severity;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	

}
