package com.javalabs.shared.dto;

import javax.ws.rs.QueryParam;

public class Payment implements Model {

	private static final long serialVersionUID = 1L;

	@QueryParam("id")
	private Long id;
	@QueryParam("kuditel_id")
	private String kuditelId;
	@QueryParam("account_number")
	private String accountNumber;
	@QueryParam("description")
	private String description;
	@QueryParam("amount")
	private String amount;
	@QueryParam("actualAmount")
	private String actualAmount;
	@QueryParam("fees")
	private String fees;
	@QueryParam("expiry_month")
	private String expiryMonth;
	@QueryParam("expiry_year")
	private String expiryYear;
	@QueryParam("holder_name")
	private String holderName;
	@QueryParam("merchant_id")
	private String merchantId;
	@QueryParam("stan")
	private String stan;
	@QueryParam("transaction_id")
	private String transactionId;
	@QueryParam("response_code")
	private String responseCode;
	@QueryParam("response_message")
	private String responseMessage;
	@QueryParam("response_url")
	private String responseUrl;
	
	public Payment() {
		this("", "", "", "", "", "", "", "", "", "", "", "", "", "", "");
	}
	
	public Payment(String kuditelId, String accountNumber, String description, String amount, String actualAmount,
			String fees, String expiryMonth, String expiryYear, String holderName, String merchantId, String stan,
			String transactionId, String responseCode, String responseMessage, String responseUrl) {
		super();
		this.kuditelId = kuditelId;
		this.accountNumber = accountNumber;
		this.description = description;
		this.amount = amount;
		this.actualAmount = actualAmount;
		this.fees = fees;
		this.expiryMonth = expiryMonth;
		this.expiryYear = expiryYear;
		this.holderName = holderName;
		this.merchantId = merchantId;
		this.stan = stan;
		this.transactionId = transactionId;
		this.responseCode = responseCode;
		this.responseMessage = responseMessage;
		this.responseUrl = responseUrl;
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

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getActualAmount() {
		return actualAmount;
	}

	public void setActualAmount(String actualAmount) {
		this.actualAmount = actualAmount;
	}

	public String getFees() {
		return fees;
	}

	public void setFees(String fees) {
		this.fees = fees;
	}

	public String getExpiryMonth() {
		return expiryMonth;
	}

	public void setExpiryMonth(String expiryMonth) {
		this.expiryMonth = expiryMonth;
	}

	public String getExpiryYear() {
		return expiryYear;
	}

	public void setExpiryYear(String expiryYear) {
		this.expiryYear = expiryYear;
	}

	public String getHolderName() {
		return holderName;
	}

	public void setHolderName(String holderName) {
		this.holderName = holderName;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getStan() {
		return stan;
	}

	public void setStan(String stan) {
		this.stan = stan;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
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

	public String getResponseUrl() {
		return responseUrl;
	}

	public void setResponseUrl(String responseUrl) {
		this.responseUrl = responseUrl;
	}
	
}
