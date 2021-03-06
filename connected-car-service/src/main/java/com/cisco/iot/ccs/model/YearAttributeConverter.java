package com.cisco.iot.ccs.model;

import java.time.Year;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class YearAttributeConverter implements AttributeConverter<Year, Short> {

	@Override
	public Short convertToDatabaseColumn(Year attribute) {
		return (short) attribute.getValue();
	}

	@Override
	public Year convertToEntityAttribute(Short dbData) {
		return Year.of(dbData);
	}
}
