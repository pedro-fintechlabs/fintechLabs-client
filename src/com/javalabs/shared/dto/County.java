package com.javalabs.shared.dto;

import javax.ws.rs.QueryParam;

public class County implements Model {

	private static final long serialVersionUID = 1L;

	@QueryParam("id")
	private Long id;
	@QueryParam("name")
	private String name;
	@QueryParam("time_stamp")
	private String timeStamp;
	
	public County() {
		this("", "");
	}
	
	public County(String name, String timeStamp) {
		super();
		this.name = name;
		this.timeStamp = timeStamp;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
