package com.javalabs.shared.dto;

import javax.ws.rs.QueryParam;

public class Refund implements Model {

	private static final long serialVersionUID = 1L;

	@QueryParam("id")
	private Long id;
	@QueryParam("kuditel_id")
	private String kuditelId;
	@QueryParam("payment_id")
	private String paymentId;
	@QueryParam("amount")
	private String amount;
	@QueryParam("response_code")
	private String responseCode;
	@QueryParam("response_message")
	private String responseMessage;
	@QueryParam("time_stamp")
	private String timeStamp;

	public Refund() {
		this("", "", "", "", "", "");
	}
	
	public Refund(String kuditelId, String paymentId, String amount, String responseCode,
			String responseMessage, String timeStamp) {
		super();
		this.kuditelId = kuditelId;
		this.paymentId = paymentId;
		this.amount = amount;
		this.responseCode = responseCode;
		this.responseMessage = responseMessage;
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

	public String getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public String getResponseMessage() {
		return responseMessage;
	}

	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
	
}
