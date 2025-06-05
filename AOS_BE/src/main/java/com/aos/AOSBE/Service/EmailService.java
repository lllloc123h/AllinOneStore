package com.aos.AOSBE.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

	@Autowired
	private JavaMailSender mailSender;

	public void sendVerificationEmail(String toEmail, String token) {
		String subject = "Account Verification";

		String htmlContent = "<html>" + "<body>" + "<p>Please enter the code to verify your account:</p>"
				+ "<div style='background-color:#007BFF; color:white; padding:16px; width:fit-content; border-radius:6px; font-size:18px;'>"
				+ token + "</div>" + "<br><p>" + token + "</p>" + "</body>" + "</html>";

		try {
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);

			helper.setTo(toEmail);
			helper.setSubject(subject);
			helper.setText(htmlContent, true); // true = isHtml

			mailSender.send(message);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
}