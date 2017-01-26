package com.indiareads.service.resources;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
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
	public ResponseEntity<GenericFormResponse> createUser(@Valid @RequestBody UserEntity user, 
			BindingResult bindingResult){

		UserEntity record;
		GenericFormResponse response = new GenericFormResponse();

		if (bindingResult.hasErrors()) {
			List<ErrorCode> errorCodeList = new ArrayList<>();
			List<ObjectError> errors = bindingResult.getAllErrors();
			for(ObjectError error : errors){
				ErrorCode errorCode = new ErrorCode(error.getCode(), error.getDefaultMessage());
				errorCodeList.add(errorCode);
			}
			response.setErrors(errorCodeList);
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}

		try{
			record = usersRepository.findByMobileNumberOrEmailAddress(user.getMobileNumber(), user.getEmailAddress());    
		}catch(Exception e){
			String message = "Error encountered while fetching users from the DB";
			log.error(message, e );
			response.setStatusCode(4048);
			ErrorCode error = new ErrorCode("DB_FETCH_ERROR", message);
			List<ErrorCode> errorsList = new ArrayList<>();
			errorsList.add(error);
			response.setErrors(errorsList);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if(record!=null){
			List<ErrorCode> errorsList = new ArrayList<>();
			response.setStatusCode(2048);
			String message = null;
			if(record.getMobileNumber().equals(user.getMobileNumber())){
				message = "Mobile number " + record.getMobileNumber() + " already exist.";
				ErrorCode error = new ErrorCode("MOBILE_NUMBER_ALREADY_EXISTS", message);	
				errorsList.add(error);
			} 
			if(record.getEmailAddress().equals(user.getEmailAddress())){
				message = "Email Address " + record.getEmailAddress() + " already exists";
				log.error(message);
				ErrorCode error = new ErrorCode("EMAIL_ADDRESS_ALREADY_EXISTS", message);	
				errorsList.add(error);
			}				
			response.setErrors(errorsList);
			log.error(message);
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}

		try{
			user.setIsActive(Boolean.TRUE);
			user.setType("BASIC");
			user.setCreatedDate(new Date());
			user.setUpdatedDate(new Date());
			usersRepository.save(user);
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
