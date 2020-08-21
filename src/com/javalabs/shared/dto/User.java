package com.javalabs.shared.dto;

import javax.ws.rs.QueryParam;

public class User implements Model {

	private static final long serialVersionUID = 1L;

	@QueryParam("id")
	private Long id;
	@QueryParam("first_name")
	private String firstName;
	@QueryParam("last_name")
	private String lastName;
	@QueryParam("email")
	private String email;
	@QueryParam("password")
	private String password;
	@QueryParam("enabled")
	private String enabled;
	@QueryParam("account_non_expired")
	private String accountNonExpired;
	@QueryParam("account_non_locked")
	private String accountNonLocked;
	@QueryParam("credentials_non_expired")
	private String credentialsNonExpired;
	@QueryParam("roles")
	private String roles;
	
	public User() {
		this("", "", "", "");
		this.roles = "";
	}
	
	public User(String email) {
		this.email = email;
	}

	public User(String firstName, String lastName, String email, String password) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEnabled() {
		return enabled;
	}

	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}

	public String getAccountNonExpired() {
		return accountNonExpired;
	}

	public void setAccountNonExpired(String accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	public String getAccountNonLocked() {
		return accountNonLocked;
	}

	public void setAccountNonLocked(String accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	public String getCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	public void setCredentialsNonExpired(String credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}
	
}
