package com.indiareads.service.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

import com.indiareads.service.util.RegexPattern;

@Entity
@Table(name = "users")
public class UserEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long    id;

	@NotNull
	@Column(name = "first_name")
	private String  firstName;

	@Column(name = "last_name")
	private String  lastName;

	@NotNull
	@Email(message = "Entered email address is not valid")
	@Column(name = "email_address", unique = true)
	private String emailAddress;

	@NotNull
	@Size(min = 10, max = 10, message = "Entered mobile number size should be 10 digits")
	@Pattern(regexp = RegexPattern.ONLY_NUMBERS, message = "Mobile number should consist of only numbers")
	@Column(name = "mobile_number", unique = true)
	private String  mobileNumber;

	private String  password;

	private String  type;

	@Column(name = "profile_photo_uri", columnDefinition="Text")
	private String  profilePhotoUri;

	@Column(name = "is_active")
	private Boolean isActive;

	@Column(name = "created_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date    createdDate;

	@Column(name = "updated_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date    updatedDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
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



	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getProfilePhotoUri() {
		return profilePhotoUri;
	}

	public void setProfilePhotoUri(String profilePhotoUri) {
		this.profilePhotoUri = profilePhotoUri;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
