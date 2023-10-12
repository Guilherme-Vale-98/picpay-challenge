package com.gui.picpaySimplified.services;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.gui.picpaySimplified.domain.User;

public interface UserService {
	
	User saveUser(User user);
	Optional<User> getUserById(UUID uuid);
	List<User> getAllUsers();
	Boolean deleteUserById(UUID uuid);
	
}
