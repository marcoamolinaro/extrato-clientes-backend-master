package com.db.extrato.util.mail;

import java.text.MessageFormat;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;

import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.db.extrato.enums.VrHorario;

import lombok.extern.java.Log;

@Log
@Component
public class ExtractInterfaceMailSender {

	private JavaMailSender mailSender;
	
	private EmailValidator validator = EmailValidator.getInstance();

	@Value("${com.db.extrato.mail.sender}")
	private String sender;

	@Value("${com.db.extrato.mail.destination}")
	private String destination;

	@Value("${com.db.extrato.mail.body}")
	private String body;

	@Value("${com.db.extrato.mail.subject}")
	private String subject;

	public void sendMailToSuccessScheduledReport(VrHorario horario) throws MailSendException {
		String bodyCompleted = body;
		bodyCompleted = MessageFormat.format(bodyCompleted, horario.name());
		ExtractMailConfig mail = new ExtractMailConfig();
		mail.setSender(sender);
		mail.setBody(bodyCompleted);
		mail.setSubject(subject);
		mail.setDestination(destination);
		sendMail(mail);
	}

	public void sendMail(ExtractMailConfig mail) throws MailSendException {
		try {
			MimeMessageHelper message = new MimeMessageHelper(mailSender.createMimeMessage(), true);
			if (mail.getSender() == null) {
				log.severe("[ExtractInterfaceMailSender] Mail sender is empty.");
				throw new MailSendException("[ExtractInterfaceMailSender] Invalid Sender");
			}
			if (!validator.isValid(mail.getSender())) {
				log.severe("[ExtractInterfaceMailSender] Mail sender is invalid - " + mail.getSender());
				throw new MailSendException("[ExtractInterfaceMailSender] Invalid Sender");
			}
			log.info("[ExtractInterfaceMailSender] Mail sender is " + mail.getSender());
			if (mail.getDestination() == null || mail.getDestination().isEmpty()) {
				log.severe("[ExtractInterfaceMailSender] Mail Destination is empty");
				throw new MailSendException("[ExtractInterfaceMailSender] Invalid Destination emails");
			}
			Set<String> validEmails = new HashSet<String>();
			for (String destination : mail.getDestination()) {
				if (validator.isValid(destination)) {
					log.info("[ExtractInterfaceMailSender] Destination mail is valid - " + destination);
					validEmails.add(destination);
				} else {
					log.warning("[ExtractInterfaceMailSender] Destination mail is invalid - " + destination + " . This email will be ignored");
				}
			}
			if (validEmails.isEmpty()) {
				log.severe("[ExtractInterfaceMailSender] Does not have valid emails");
				throw new MailSendException("[ExtractInterfaceMailSender] Does not have valid emails");
			}
			message.setFrom(mail.getSender());
			message.setTo(validEmails.toArray(new String[validEmails.size()]));
			message.setText(mail.getBody() + "\n\n\n ");
			message.setSubject(mail.getSubject());
			mailSender.send(message.getMimeMessage());
		} catch (Exception e) {
			log.log(Level.SEVERE, "[ExtractInterfaceMailSender] Error sent mail", e);
			throw new MailSendException("Error sent mail", e);
		}

	}

}
