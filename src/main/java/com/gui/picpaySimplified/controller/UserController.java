package com.gui.picpaySimplified.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.gui.picpaySimplified.domain.User;
import com.gui.picpaySimplified.services.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class UserController {

    public static final String USER_PATH = "/v1/users";
    public static final String USER_PATH_ID = USER_PATH + "/{userId}";
    
    @Autowired
    UserService userService;
    
    @GetMapping(value = USER_PATH)
    public List<User> getAllUsers() {
    	return userService.getAllUsers();
    }
    
    @PostMapping(value = USER_PATH)
	public ResponseEntity postUser(@Validated @RequestBody User user) {
    	User savedUser = userService.saveUser(user);
    	HttpHeaders headers = new HttpHeaders();
        headers.add("Location", USER_PATH + "/" + savedUser.getId().toString());
        return new ResponseEntity(headers, HttpStatus.CREATED);   
    }
    
    
    @GetMapping(value = USER_PATH_ID)
    public User getUserById(@PathVariable("userId") UUID userId){

        return userService.getUserById(userId).orElseThrow();
    }
    
    @DeleteMapping(value = USER_PATH_ID)
    public ResponseEntity deleteUserById(@PathVariable("userId") UUID userId) throws Exception{
        if(!userService.deleteUserById(userId)) {
        	throw new Exception("User not found");
        }
    	
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
    
    
}
