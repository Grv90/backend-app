package com.indiareads.service.resources;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.indiareads.service.domain.UserEntity;
import com.indiareads.service.model.ErrorCode;
import com.indiareads.service.model.GenericFormResponse;
import com.indiareads.service.model.LoginRequest;
import com.indiareads.service.model.LoginResponse;
import com.indiareads.service.model.SuccessCode;
import com.indiareads.service.repo.UsersRepository;

/**
 *  @version     1.0, 30-Dec-2016
 *  @author gaurav
 */
@RestController
@RequestMapping(value="/v1/users")
public class UsersResource {

	private static final Logger log = LoggerFactory.getLogger(UsersResource.class);   

	@Autowired
	private UsersRepository usersRepository;

	@RequestMapping(method = RequestMethod.GET)
	public  ResponseEntity<List<UserEntity>> getAllUsers() {
		return new ResponseEntity<>((List<UserEntity>)usersRepository.findAll(), HttpStatus.OK);
	}


	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<GenericFormResponse> createUser(@RequestBody UserEntity users){
		UserEntity record=null;
		GenericFormResponse response = new GenericFormResponse();
		try{
			record = usersRepository.findByMobileNumber(users.getMobileNumber());    
		}catch(Exception e){
			log.error("Error encountered while fetching users from repo", e);
		}

		if(record!=null){
			String message="Mobile number " + record.getMobileNumber() + " already exist.";
			log.error("found the record with mobile number"+users.getMobileNumber());
			response.setStatusCode(2048);
			ErrorCode error = new ErrorCode("USER_ALREADY_EXISTS", message);
			List<ErrorCode> errorsList = new ArrayList<>();
			errorsList.add(error);
			response.setErrors(errorsList);
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}

		try{
			users.setIsActive(Boolean.TRUE);
			users.setType("BASIC");
			users.setCreatedDate(new Date());
			users.setUpdatedDate(new Date());
			usersRepository.save(users);
		}
		catch(Exception e){
			String message = "Exception occured while persisting into DB";
			log.error(message, e );
			response.setStatusCode(4048);
			ErrorCode error = new ErrorCode("DB_PERSISTANCE_ERROR", message);
			List<ErrorCode> errorsList = new ArrayList<>();
			errorsList.add(error);
			response.setErrors(errorsList);
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}

		String message = "You have Successfully registered with India Reads.";
		response.setStatusCode(1002);
		SuccessCode success = new SuccessCode("OK", message);
		List<SuccessCode> successList = new ArrayList<>();
		successList.add(success);
		response.setSuccess(successList);
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	@RequestMapping(value="/login", method=RequestMethod.POST)
	public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest){
		UserEntity user=null;
		LoginResponse loginResponse = new LoginResponse();
		try{
			user = usersRepository.findOneByEmailAddressOrMobileNumberAndPassword(loginRequest.getUsername(), loginRequest.getPassword(), Boolean.TRUE);    
		}catch(Exception e){
			String message = "Error encountered while fetching user from the system";
			log.error(message, e);
			loginResponse.setStatusCode(4048);
			ErrorCode error = new ErrorCode("DB_FETCH_ERROR", message);
			List<ErrorCode> errorsList = new ArrayList<>();
			errorsList.add(error);
			loginResponse.setErrors(errorsList);
			return new ResponseEntity<>(loginResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if(user==null){
			String message = "Invalid Login Credentials. Kindly try again with a valid one";
			log.error(message);
			loginResponse.setStatusCode(2048);
			ErrorCode error = new ErrorCode("USER_NOT_FOUND", message);
			List<ErrorCode> errorsList = new ArrayList<>();
			errorsList.add(error);
			loginResponse.setErrors(errorsList);
			return new ResponseEntity<>(loginResponse, HttpStatus.BAD_REQUEST);
		}

		loginResponse = setAttributesInLoginResponse(user);
		String message = "User successfully retrieved from the server";
		loginResponse.setStatusCode(1002);
		SuccessCode success = new SuccessCode("OK", message);
		List<SuccessCode> successList = new ArrayList<>();
		successList.add(success);
		loginResponse.setSuccess(successList);

		return new ResponseEntity<>(loginResponse, HttpStatus.OK);

	}


	private LoginResponse setAttributesInLoginResponse(UserEntity user) {
		LoginResponse loginResponse = new LoginResponse();
		loginResponse.setFirstName(user.getFirstName());
		loginResponse.setLastName(user.getLastName());
		loginResponse.setEmailAddress(user.getEmailAddress());
		loginResponse.setProfilePhotoUri(user.getProfilePhotoUri());
		loginResponse.setMobileNumber(user.getMobileNumber());
		loginResponse.setUserType(user.getType());
		return loginResponse;
	}

}
