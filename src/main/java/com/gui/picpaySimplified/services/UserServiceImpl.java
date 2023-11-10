package com.gui.picpaySimplified.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.gui.picpaySimplified.controller.NotFoundException;
import com.gui.picpaySimplified.domain.User;
import com.gui.picpaySimplified.repositories.UserRepository;


@Service
public class UserServiceImpl implements UserService {
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Override
	public User saveUser(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		User savedUser = userRepository.save(user);
		return savedUser;				
	}

	@Override
	public Optional<User> getUserById(UUID uuid) {
		return userRepository.findById(uuid);
	}

	@Override
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	@Override
	public Boolean deleteUserById(UUID uuid) {
		if (userRepository.existsById(uuid)) {
			userRepository.deleteById(uuid);
			return true;
		}
		return false;
	}
	
	

}
