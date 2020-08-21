package com.javalabs.shared.dto;

import javax.ws.rs.QueryParam;

public class Country implements Model {

	private static final long serialVersionUID = 1L;

	@QueryParam("id")
	private Long id;
	@QueryParam("kuditel_id")
	private String kuditelId;
	@QueryParam("name")
	private String name;
	@QueryParam("currency")
	private String currency;
	@QueryParam("phone_code")
	private String phoneCode;
	@QueryParam("iso_code")
	private String isoCode;
	@QueryParam("number_code")
	private String numberCode;
	@QueryParam("time_stamp")
	private String timeStamp;
	
	public Country() {
		this("", "", "", "", "", "", "");
	}
	
	public Country(String kuditelId, String name, String currency, String phoneCode, String isoCode, String numberCode, String timeStamp) {
		super();
		this.kuditelId = kuditelId;
		this.name = name;
		this.currency = currency;
		this.phoneCode = phoneCode;
		this.isoCode = isoCode;
		this.numberCode = numberCode;
		this.timeStamp = timeStamp;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getKuditelId() {
		return kuditelId;
	}

	public void setKuditelId(String kuditelId) {
		this.kuditelId = kuditelId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getPhoneCode() {
		return phoneCode;
	}

	public void setPhoneCode(String phoneCode) {
		this.phoneCode = phoneCode;
	}

	public String getIsoCode() {
		return isoCode;
	}

	public void setIsoCode(String isoCode) {
		this.isoCode = isoCode;
	}

	public String getNumberCode() {
		return numberCode;
	}

	public void setNumberCode(String numberCode) {
		this.numberCode = numberCode;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
	
}
