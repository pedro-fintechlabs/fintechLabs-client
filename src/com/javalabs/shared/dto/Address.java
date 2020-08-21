package com.javalabs.shared.dto;

import javax.ws.rs.QueryParam;

public class Address implements Model {

	private static final long serialVersionUID = 1L;

	@QueryParam("id")
	private Long id;
	@QueryParam("country_id")
	private Long countryId;
	@QueryParam("state_id")
	private Long stateId;
	@QueryParam("city_id")
	private Long cityId;
	@QueryParam("county_id")
	private Long countyId;
	@QueryParam("zipcode_id")
	private Long zipcodeId;
	@QueryParam("kuditel_id")
	private String kuditelId;
	@QueryParam("street_name")
	private String streetName;
	@QueryParam("street_number")
	private String streetNumber;
	@QueryParam("addressable")
	private String addressable;
	@QueryParam("is_verified")
	private String isVerified;
	@QueryParam("time_stamp")
	private String timeStamp;
	
	public Address() {
		this(null, null, null, null, null, "", "", "", "", "", "", "", "");
	}
	
	public Address(Long countryId, Long stateId, Long cityId, Long countyId, Long zipcodeId, String city, String state, String kuditelId, String streetName, String postalCode,
			String addressable, String isVerified, String timeStamp) {
		super();
		this.countryId = countryId;
		this.stateId = stateId;
		this.cityId = cityId;
		this.countyId = countyId;
		this.zipcodeId = zipcodeId;
		this.kuditelId = kuditelId;
		this.streetName = streetName;
		this.addressable = addressable;
		this.isVerified = isVerified;
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

	public Long getCityId() {
		return cityId;
	}

	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}

	public Long getCountyId() {
		return countyId;
	}

	public void setCountyId(Long countyId) {
		this.countyId = countyId;
	}
	
	public Long getZipcodeId() {
		return zipcodeId;
	}

	public void setZipcodeId(Long zipcodeId) {
		this.zipcodeId = zipcodeId;
	}

	public String getKuditelId() {
		return kuditelId;
	}

	public void setKuditelId(String kuditelId) {
		this.kuditelId = kuditelId;
	}

	public String getStreetName() {
		return streetName;
	}

	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}

	public String getStreetNumber() {
		return streetNumber;
	}

	public void setStreetNumber(String streetNumber) {
		this.streetNumber = streetNumber;
	}

	public String getAddressable() {
		return addressable;
	}

	public void setAddressable(String addressable) {
		this.addressable = addressable;
	}

	public String getIsVerified() {
		return isVerified;
	}

	public void setIsVerified(String isVerified) {
		this.isVerified = isVerified;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
		
}
