package com.indiareads.service.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ErrorCode {

	@JsonProperty
	private String code;

	@JsonProperty
	private String fieldName;

	protected ErrorCode() {
	}

	public ErrorCode(String code, String fieldName) {
		this.code = code;
		this.fieldName = fieldName;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}


}
