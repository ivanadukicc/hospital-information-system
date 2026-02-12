package com.ivana.demo.services;

import java.util.Date;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

	private JavaMailSender mailSender;

	public EmailService(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}
	 public void sendSimpleMail(String to, String subject, String text) {
	        SimpleMailMessage msg = new SimpleMailMessage();
	        msg.setTo(to);
	        msg.setSubject(subject);
	        msg.setText(text);
	        mailSender.send(msg);
	    }
	 public void sendTerminConfirmation(String to, String imePacijenta, Date pocetak) {
	        try {
				String subject = "Potvrda zakazanog termina";
				String text = "Poštovani " + imePacijenta + ",\n\n"
				        + "Uspešno ste zakazali termin za: " + pocetak + ".\n\n"
				        + "Hvala što koristite naš sistem.\nBolnica";

				sendSimpleMail(to, subject, text);
				System.out.println("Mejl uspešno poslat na: " + to);
			} catch (Exception e) {
				System.out.println("Greška prilikom slanja mejla: " + e.getMessage());
				e.printStackTrace();
			}
	    }
}
