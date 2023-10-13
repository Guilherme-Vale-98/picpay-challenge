package com.gui.picpaySimplified.services;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.gui.picpaySimplified.controller.NotFoundException;
import com.gui.picpaySimplified.domain.Transaction;
import com.gui.picpaySimplified.domain.User;
import com.gui.picpaySimplified.repositories.TransactionRepository;

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
		User payer = userService.getUserById(payerId).orElseThrow(()-> new NotFoundException("Payer not found"));
		User payee = userService.getUserById(payeeId).orElseThrow(()-> new NotFoundException("Payee not found"));
		
		this.validateTransaction(payer, payee, amount);
			
		Transaction newTransaction = new Transaction(payeeId, payer, payee, amount, LocalDateTime.now());
		payer.setBalance(payer.getBalance().subtract(amount));
		payee.setBalance(payee.getBalance().add(amount));
		userService.saveUser(payee);
		userService.saveUser(payer);
		
		transactionRepository.save(newTransaction);
		return newTransaction;
	}

	private Boolean validateTransaction(User payer, User payee, BigDecimal amount) {	
		if(payer==payee) {
			throw new InvalidTransactionException("Cannot make transfer to own account");
		}
		if(payer.getBalance().compareTo(amount) < 0) {
			throw new InvalidTransactionException("Not enough balance");
		}
		if(payer.getUserType().toString() == "SHOPKEEPER") {
			throw new InvalidTransactionException("Shopkeepers cannot execute transaction");
		}
		
		ResponseEntity<Map> authorization = restTemplate.getForEntity("https://run.mocky.io/v3/5e325067-364a-40ef-806d-febfe3e9c1da", Map.class);
		if(authorization.getStatusCode() == HttpStatus.OK && ((String) authorization.getBody().get("message")).equalsIgnoreCase("autorizado") != true) {
			System.out.println(((String) authorization.getBody().get("message")).equalsIgnoreCase("autorizado"));
			throw new InvalidTransactionException("Not authorized!");			
		}
		return true;		
	}

	@Override
	public Optional<List<Transaction>> listTransactionsByPayeeId(UUID payeeId) {
		User payee = userService.getUserById(payeeId).orElseThrow(NotFoundException::new);
		return transactionRepository.findAllByPayee(payee);	
	}

	@Override
	public Optional<List<Transaction>> listTransactionsByPayerId(UUID payerId) {
		User payer = userService.getUserById(payerId).orElseThrow(NotFoundException::new);
		return transactionRepository.findAllByPayer(payer);
	}

	




}
