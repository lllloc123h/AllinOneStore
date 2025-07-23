package com.aos.AOSBE.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {
	private final JavaMailSender mailSender;

	@Autowired
	public EmailService(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	public void sendVerificationEmail(JavaMailSender mailSender, String toEmail, String token) {
		String subject = "Account Verification";

		String htmlContent = """
						<html lang="en">
				<head>
				  <meta charset="UTF-8" />
				  <title>Verification Code</title>
				</head>
				<body
				  style="
				    font-family: Arial, sans-serif;
				    background-color: #f4f4f4;
				    padding: 20px;
				  "
				>
				  <table
				    width="100%"
				    style="
				      max-width: 600px;
				      margin: auto;
				      background-color: #ffffff;
				      padding: 20px;
				      border-radius: 8px;
				      box-shadow: 0 0 10px #ccc;
				    "
				  >
				    <tr>
				      <td align="center">
				        <h2 style="color: #333">Your Verification Code</h2>
				        <p style="font-size: 16px; color: #666">
				          Hello, please use the code below to verify your Account:
				        </p>
				        <p
				          style="
				            font-size: 32px;
				            font-weight: bold;
				            color: #2c3e50;
				            margin: 20px 0;
				          "
				        >
				          <span style="letter-spacing: 5px">
				          """ + token + """
				            		</span>
				            <!-- Replace with your dynamic code -->
				          </p>
				          <p style="font-size: 14px; color: #999">
				            This code will expire in 1 minutes.
				          </p>
				          <p style="margin-top: 40px; font-size: 12px; color: #bbb">
				            If you did not request this code, please ignore this email.
				          </p>
				        </td>
				      </tr>
				    </table>
				  </body>
				</html>
				            		""";
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

	public void sendVerificationEWallet(String toEmail, String token) {
		String subject = "Ewallet All-In-One Store Verification";

		String htmlContent = """
						<html lang="en">
				<head>
				  <meta charset="UTF-8" />
				  <title>Verification Code</title>
				</head>
				<body
				  style="
				    font-family: Arial, sans-serif;
				    background-color: #f4f4f4;
				    padding: 20px;
				  "
				>
				  <table
				    width="100%"
				    style="
				      max-width: 600px;
				      margin: auto;
				      background-color: #ffffff;
				      padding: 20px;
				      border-radius: 8px;
				      box-shadow: 0 0 10px #ccc;
				    "
				  >
				    <tr>
				      <td align="center">
				        <h2 style="color: #333">Your Verification Code</h2>
				        <p style="font-size: 16px; color: #666">
				          Hello, please use the code below to verify your Ewallet:
				        </p>
				        <p
				          style="
				            font-size: 32px;
				            font-weight: bold;
				            color: #2c3e50;
				            margin: 20px 0;
				          "
				        >
				          <span style="letter-spacing: 5px">
				          """ + token + """
				            		</span>
				            <!-- Replace with your dynamic code -->
				          </p>
				          <p style="font-size: 14px; color: #999">
				            This code will expire in 1 minutes.
				          </p>
				          <p style="margin-top: 40px; font-size: 12px; color: #bbb">
				            If you did not request this code, please ignore this email.
				          </p>
				        </td>
				      </tr>
				    </table>
				  </body>
				</html>
				            		""";

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