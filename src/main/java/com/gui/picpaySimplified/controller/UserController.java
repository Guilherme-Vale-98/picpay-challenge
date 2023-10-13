package com.gui.picpaySimplified.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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

import com.gui.picpaySimplified.domain.Transaction;
import com.gui.picpaySimplified.domain.User;
import com.gui.picpaySimplified.services.TransactionService;
import com.gui.picpaySimplified.services.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class UserController {

    public static final String USER_PATH = "/v1/users";
    public static final String USER_PATH_ID = USER_PATH + "/{userId}";
    public static final String USER_TRANSACTION_PATH = USER_PATH+"/transaction";
   
    
    @Autowired
    UserService userService;
    
    @Autowired
    TransactionService transactionService;
    
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

        return userService.getUserById(userId).orElseThrow(()->  new NotFoundException("User not found."));
    }
    
    @DeleteMapping(value = USER_PATH_ID)
    public ResponseEntity deleteUserById(@PathVariable("userId") UUID userId) throws Exception{
        if(!userService.deleteUserById(userId)) {
        	throw new NotFoundException("User not found!");
        }
    	
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
    
    @PostMapping(value = USER_TRANSACTION_PATH)
 	public ResponseEntity postTransaction(@Validated @RequestBody Map body ){
     	
    	UUID payerId = UUID.fromString(body.get("payerId").toString());
    	UUID payeeId = UUID.fromString(body.get("payeeId").toString());
     	BigDecimal amount = BigDecimal.valueOf((Integer) body.get("amount")) ;
    	
     	Transaction newTransaction = transactionService.executeTransaction(payerId, payeeId, amount);
        
     	return new ResponseEntity(HttpStatus.CREATED);   
     }
     

    @GetMapping(value = USER_PATH_ID + "/payments")
    public Optional<List<Transaction>> getAllTransactionsByPayer(@PathVariable("userId") UUID payerId) {
    	return transactionService.listTransactionsByPayerId(payerId);
    }
    
    @GetMapping(value = USER_PATH_ID + "/earnings")
    public Optional<List<Transaction>> getAllTransactionsByPayee(@PathVariable("userId") UUID payeeId) {
    	return transactionService.listTransactionsByPayeeId(payeeId);
    }
    
}
