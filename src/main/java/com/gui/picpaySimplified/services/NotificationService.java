package com.gui.picpaySimplified.services;

import com.gui.picpaySimplified.domain.User;


public interface NotificationService {
	
	void sendNotification(User payer, User payee);

}
