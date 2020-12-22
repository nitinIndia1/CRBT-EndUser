package com.crbt.api.services.bean;

public class ErrorMessage {

	private String errorField;
	private String errorCode;
	private String errorDescription;

	public ErrorMessage() {

	}

	public ErrorMessage(String errorField, String errorCode, String errorDescription) {
		super();
		this.errorField = errorField;
		this.errorCode = errorCode;
		this.errorDescription = errorDescription;
	}

	public String getErrorField() {
		return errorField;
	}

	public void setErrorField(String errorField) {
		this.errorField = errorField;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorDescription() {
		return errorDescription;
	}

	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}

	@Override
	public String toString() {
		return "ErrorMessage [errorField=" + errorField + ", errorCode=" + errorCode + ", errorDescription="
				+ errorDescription + "]";
	}

}
