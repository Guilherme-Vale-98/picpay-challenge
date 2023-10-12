package com.gui.picpaySimplified.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.gui.picpaySimplified.domain.Transaction;
import com.gui.picpaySimplified.domain.User;

public interface TransactionService {
	Transaction executeTransaction(UUID payerId, UUID payeeId, BigDecimal amount);
	Optional<List<Transaction>> listTransactionsByPayeeId(UUID payeeId);
	Optional<List<Transaction>> getTransactionsByPayerId(UUID payerId);	
}
