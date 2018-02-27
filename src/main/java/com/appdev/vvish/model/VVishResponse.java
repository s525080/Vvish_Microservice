package com.appdev.vvish.model;

public class VVishResponse {

	private String status; //SUCCESS or FAILURE
	private String statusDescription;
	private ErrorDetails errorDetails;
	
	public VVishResponse(String status, String statusDescription) {
		this.status = status;
		this.statusDescription = statusDescription;
	}
	
	public VVishResponse(String status, String errorCode, String errorDescription) {
		this.status = status;
		this.errorDetails = new ErrorDetails(errorCode, errorDescription);
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getStatusDescription() {
		return statusDescription;
	}
	public void setStatusDescription(String statusDescription) {
		this.statusDescription = statusDescription;
	}
	
	public ErrorDetails getErrorDetails() {
		return errorDetails;
	}
	public void setErrorDetails(ErrorDetails errorDetails) {
		this.errorDetails = errorDetails;
	}
	
}

class ErrorDetails {
	
	private String errorCode;
	private String errorDescription;
	
	public ErrorDetails(String errorCode, String errorDescription) {
		this.errorCode = errorCode;
		this.errorDescription = errorDescription;
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
	
}