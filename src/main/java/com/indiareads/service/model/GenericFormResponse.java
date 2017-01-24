package com.indiareads.service.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GenericFormResponse {

	@JsonProperty(value="reason_code")
	private Integer statusCode;

	@JsonProperty(value="errors")
	private List<ErrorCode> errors;

	@JsonProperty(value="success")
	private List<SuccessCode> success;

	public Integer getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(Integer statusCode) {
		this.statusCode = statusCode;
	}

	public List<ErrorCode> getErrors() {
		return errors;
	}

	public void setErrors(List<ErrorCode> errors) {
		this.errors = errors;
	}

	public List<SuccessCode> getSuccess() {
		return success;
	}

	public void setSuccess(List<SuccessCode> success) {
		this.success = success;
	}


}
