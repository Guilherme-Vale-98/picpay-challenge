package com.gui.picpaySimplified.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.gui.picpaySimplified.domain.Transaction;
import com.gui.picpaySimplified.domain.User;
import com.gui.picpaySimplified.repositories.TransactionRepository;
import com.gui.picpaySimplified.repositories.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class TransactionServiceImpl implements TransactionService {
	@Autowired
	UserService userService;
	
	@Autowired
	TransactionRepository transactionRepository;
	
	@Autowired
	RestTemplate restTemplate;
	 
	
	
	@Override
	@Transactional
	public Transaction executeTransaction(UUID payerId, UUID payeeId, BigDecimal amount) {
		User payer = userService.getUserById(payerId).orElseThrow();
		User payee = userService.getUserById(payeeId).orElseThrow();
		
		Boolean isAuthorized = this.validateTransaction(payer, payee, amount);
		
		if(!isAuthorized) {
			return null;
		}
		
		return null;
	}

	private Boolean validateTransaction(User payer, User payee, BigDecimal amount) {
		if(payer.getBalance().compareTo(amount) < 0) {
			return false;
		}
		if(payer.getUserType().toString() == "SHOPKEEPER") {
			return false;
		}
		
		ResponseEntity<Map> authorization = restTemplate.getForEntity("https://run.mocky.io/v3/5e325067-364a-40ef-806d-febfe3e9c1da", Map.class);
		if(authorization.getStatusCode() == HttpStatus.OK && (String) authorization.getBody().get("message") != "Autorizado") {
			return false;				
		}
		return true;		
	}

	@Override
	public Optional<List<Transaction>> listTransactionsByPayeeId(UUID payeeId) {
		User payee = userService.getUserById(payeeId).orElseThrow();
		return transactionRepository.findAllByPayee(payee);	
	}

	@Override
	public Optional<List<Transaction>> getTransactionsByPayerId(UUID payerId) {
		User payer = userService.getUserById(payerId).orElseThrow();
		return transactionRepository.findAllByPayee(payer);
	}

	




}
