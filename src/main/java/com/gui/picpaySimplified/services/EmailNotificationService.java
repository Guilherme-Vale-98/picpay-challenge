package com.gui.picpaySimplified.services;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.gui.picpaySimplified.domain.User;

import jakarta.mail.MessagingException;
import jakarta.mail.SendFailedException;

@Service
public class EmailNotificationService implements NotificationService {
	
	@Autowired
	JavaMailSender mailSender;

	@Override
	public void sendNotification(User payer, User payee) {
		SimpleMailMessage messageToPayer = new SimpleMailMessage();
		messageToPayer.setText("Hello, Dear " + payer.getFirstName() + "!\r\n"
				+ "\r\n"
				+ "Congratulations, your payment was completed.\r\n"
				+ "\r\n"
				+ "Thank you for using our services.\r\n"
				+ "\r\n"
				+ "Respectfully, PicPaySimplified.");
		messageToPayer.setSubject("SimplifiedPicPay");
		messageToPayer.setFrom("guilherme.vale98@gmail.com");
		messageToPayer.setTo(payer.getEmail());
		
		SimpleMailMessage messageToPayee = new SimpleMailMessage();
		messageToPayee.setText("You received a payment");
		messageToPayee.setSubject("SimplifiedPicPay");
		messageToPayee.setTo(payee.getEmail());
		
		mailSender.send(messageToPayee);
		mailSender.send(messageToPayer);
		
	}


}
