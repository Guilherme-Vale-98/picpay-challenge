package com.gui.picpaySimplified.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gui.picpaySimplified.domain.Transaction;
import com.gui.picpaySimplified.domain.User;

public interface TransactionRepository extends JpaRepository<Transaction, UUID>{
	Optional<List<Transaction>> findAllByPayer(User Payer);
	Optional<List<Transaction>> findAllByPayee(User Payee);
}
