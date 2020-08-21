package com.javalabs.shared.dto;

import javax.ws.rs.QueryParam;

public class State implements Model {

	private static final long serialVersionUID = 1L;

	@QueryParam("id")
	private Long id;
	@QueryParam("country_id")
	private Long countryId;
	@QueryParam("name")
	private String name;
	@QueryParam("time_stamp")
	private String timeStamp;
	
	public State() {
		this(0L, "", "");
	}
	
	public State(Long countryId, String name, String timeStamp) {
		super();
		this.countryId = countryId;
		this.name = name;
		this.timeStamp = timeStamp;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCountryId() {
		return countryId;
	}

	public void setCountryId(Long countryId) {
		this.countryId = countryId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
