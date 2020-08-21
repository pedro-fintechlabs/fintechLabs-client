package com.javalabs.shared.dto;

import javax.ws.rs.QueryParam;

public class City implements Model {

	private static final long serialVersionUID = 1L;

	@QueryParam("id")
	private Long id;
	@QueryParam("country_id")
	private Long countryId;
	@QueryParam("state_id")
	private Long stateId;
	@QueryParam("name")
	private String name;
	@QueryParam("time_stamp")
	private String timeStamp;
	
	public City() {
		this(0L, 0L, "", "");
	}
	
	public City(Long countryId, Long stateId, String name, String timeStamp) {
		super();
		this.countryId = countryId;
		this.stateId = stateId;
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

	public Long getStateId() {
		return stateId;
	}

	public void setStateId(Long stateId) {
		this.stateId = stateId;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
