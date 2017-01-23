
package com.learn.controller;

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

import com.learn.model.Response;
import com.learn.repo.UsersRepo;
import com.learn.store.UsersEntity;


/*
 *  @version     1.0, 30-Dec-2016
 *  @author gaurav
*/
@RestController
public class Users {
    
    private static final Logger log = LoggerFactory.getLogger(Users.class);   


    @Autowired
    private UsersRepo repo;
//list all the registered users 
    @RequestMapping(value="/users",method = RequestMethod.GET)
    public  ResponseEntity<List<UsersEntity>> getAllUsers() {
        
//        List<UsersEntity> users =(List<UsersEntity>)repo.findAll(); 
//        List<UsersResponse> usersResponse = users.stream().map(user ->{ return new UsersResponse(user.getId(), user.getFirstName(),user.getFirstName());
//        }).collect(Collectors.toList());
//        
        return new ResponseEntity<>((List<UsersEntity>)repo.findAll(), HttpStatus.OK);
    }
    
    
  //create a new user signup   
    @RequestMapping(value="/create",method=RequestMethod.POST)
    public ResponseEntity<Response<UsersEntity>> createUser(@RequestBody UsersEntity users){
        UsersEntity record=null;
        Response<UsersEntity> userResponse = new Response<>();
        
       

        try{
          //check if the mobile number is already registered or not
             record=repo.findByMobileNumber(users.getMobileNumber());    
        }
        catch(Exception e){
            log.error("Error", e);
        }
        
        if(record!=null){
            String message="Mobile number "+record.getMobileNumber()+ " already exist.";
            userResponse = builtErrorResponse(userResponse, HttpStatus.PRECONDITION_FAILED,message,null);
            log.error("found the record with mobile number"+users.getMobileNumber());
            return new ResponseEntity<>(userResponse, HttpStatus.BAD_REQUEST);

        }
        
        try{
            users.setCreatedDate(new Date());
            users.setUpdatedDate(new Date());
            repo.save(users);
        }
        catch(Exception e){
            log.error("Exception occured while persisting into DB", e );
            userResponse = builtErrorResponse(userResponse, HttpStatus.BAD_REQUEST,e.getMessage(),e.getMessage());
            return new ResponseEntity<>(userResponse, HttpStatus.INTERNAL_SERVER_ERROR);

        }
        
        userResponse.setMessage("You have Successfully registered with India Reads.");
        return new ResponseEntity<>(userResponse,HttpStatus.OK);
        
    }

 public <T> Response<T> builtErrorResponse (Response<T> response ,HttpStatus httpStatus ,String errorMsg ,String responseMsg){
        
        List<com.learn.model.Error> errors = new ArrayList<>();
        com.learn.model.Error error= new com.learn.model.Error(httpStatus.value(), errorMsg);
        errors.add(error);
        response.setMessage(responseMsg);
        response.setError(errors);
        return response;
       
    }
    

}
