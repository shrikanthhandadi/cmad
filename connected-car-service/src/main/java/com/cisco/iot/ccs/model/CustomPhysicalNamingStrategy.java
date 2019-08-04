package com.cisco.iot.ccs.model;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategy;
import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

public class CustomPhysicalNamingStrategy extends PhysicalNamingStrategyStandardImpl implements PhysicalNamingStrategy {

	private static final long serialVersionUID = 7933684233099600149L;

	@Override
	public Identifier toPhysicalColumnName(final Identifier identifier, final JdbcEnvironment jdbcEnv) {
		return convertToSnakeCase(identifier);
	}

	@Override
	public Identifier toPhysicalTableName(Identifier name, JdbcEnvironment context) {
		return convertToSnakeCase(name);
	}

	private Identifier convertToSnakeCase(final Identifier identifier) {
		final String regex = "([a-z])([A-Z])";
		final String replacement = "$1_$2";
		final String newName = identifier.getText().replaceAll(regex, replacement).toLowerCase();
		return Identifier.toIdentifier(newName);
	}
}
