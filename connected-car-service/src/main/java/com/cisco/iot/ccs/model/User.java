package com.cisco.iot.ccs.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;

/**
 * {@link User} of the CCS console. Not an end customer.
 * 
 * @author shandadi
 *
 */
@Entity
public class User {

	@Id
	@GeneratedValue
	private Long id;

	@Column(unique = true, nullable = false, length = 45)
	private String username;

	@Column(nullable = false, length = 100)
	private String password;

	@ElementCollection(targetClass = String.class)
	@JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
	@Column(name = "roles", nullable = false)
	private Set<String> roles;

	@ElementCollection(targetClass = String.class)
	@JoinTable(name = "user_make", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
	@Column(name = "makes", nullable = false)
	private Set<String> makes;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<String> getRoles() {
		return roles;
	}

	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}

	public Set<String> getMakes() {
		return makes;
	}

	public void setMakes(Set<String> makes) {
		this.makes = makes;
	}

}
