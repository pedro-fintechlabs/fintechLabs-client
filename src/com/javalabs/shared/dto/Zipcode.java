package com.javalabs.shared.dto;

import javax.ws.rs.QueryParam;

public class Zipcode implements Model {

	private static final long serialVersionUID = 1L;

	@QueryParam("id")
	private Long id;
	@QueryParam("zip")
	private String zip;
	@QueryParam("county_id")
	private Long countyId;
	@QueryParam("time_stamp")
	private String timeStamp;
	
	public Zipcode() {
		this("", 0L, "");
	}
	
	public Zipcode(String zip, Long countyId, String timeStamp) {
		super();
		this.zip = zip;
		this.countyId = countyId;
		this.timeStamp = timeStamp;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}
	
	public Long getCountyId() {
		return countyId;
	}

	public void setCountyId(Long countyId) {
		this.countyId = countyId;
	}
	
}
