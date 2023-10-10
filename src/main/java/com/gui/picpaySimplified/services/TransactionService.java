package com.gui.picpaySimplified.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;


import com.gui.picpaySimplified.domain.Transaction;
import com.gui.picpaySimplified.domain.User;

public interface TransactionService {
	Transaction executeTransaction(User payer, User payee, BigDecimal amount);
	Optional<List<Transaction>> getTransactionsByPayee();	
}
