package com.cisco.iot.ccs.model;

public class JwtAuthenticationResponse {

	private String token;
	private final String type = "Bearer";

	public static JwtAuthenticationResponse token(String token) {
		JwtAuthenticationResponse res = new JwtAuthenticationResponse();
		res.setToken(token);
		return res;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getType() {
		return type;
	}

}
