package com.indiareads.service.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class LoginResponse extends GenericFormResponse{
    
    @JsonProperty
    private Long id;
    
	@JsonProperty
	private String firstName;

	@JsonProperty
	private String lastName;

	@JsonProperty
	private String mobileNumber;

	@JsonProperty
	private String emailAddress;

	@JsonProperty
	private String userType;

	@JsonProperty
	private String profilePhotoUri;

	public LoginResponse() {
	}

	public String getFirstName() {
		return firstName;
	}
	
	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getProfilePhotoUri() {
		return profilePhotoUri;
	}

	public void setProfilePhotoUri(String profilePhotoUri) {
		this.profilePhotoUri = profilePhotoUri;
	}


}