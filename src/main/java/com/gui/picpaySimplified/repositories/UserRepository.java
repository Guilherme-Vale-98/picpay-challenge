package com.gui.picpaySimplified.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gui.picpaySimplified.domain.User;

public interface UserRepository extends JpaRepository<User, UUID> {
	Optional<User> findUserByDocument(String document);
	
}
