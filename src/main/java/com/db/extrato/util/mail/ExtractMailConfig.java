package com.db.extrato.util.mail;

import java.util.ArrayList;
import java.util.List;

public class ExtractMailConfig {
	
	/**
     * Subject of the e-mail
     */
    private String subject;
    /**
     * The sender mail
     */
    private String sender;
    /**
     * Array of recipients
     */
    private List<String> destination = new ArrayList<String>();

	/**
     * Text to send in the e-mail body
     */
    private String body;

    /**
     * Indicates if the attachments should be compressed in a single file. This
     * flag overrides the "compressed" file from MailAttachment class.
     */
    private boolean singleAttachedFile;
    private boolean selfCopy;
    
    private boolean compress;

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender.trim();
	}

	public List<String> getDestination() {
		return destination;
	}

	public void setDestination(List<String> destination) {
		this.destination = destination;
	}
	
	public void setDestination (String emails)
	{
		if(emails == null || emails.isEmpty())
		{
			this.destination = null;
			return;
		}
		
		this.destination = new ArrayList<String>();
		
		String[] splitedEmails = emails.split(";");
		
		for (String email : splitedEmails)
		{
			destination.add(email);
		}
		
		splitedEmails = emails.split(",");
		
		for (String email: splitedEmails)
		{
			destination.add(email);
		}
		
	}
	

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public boolean isSingleAttachedFile() {
		return singleAttachedFile;
	}

	public void setSingleAttachedFile(boolean singleAttachedFile) {
		this.singleAttachedFile = singleAttachedFile;
	}

	public boolean isSelfCopy() {
		return selfCopy;
	}

	public void setSelfCopy(boolean selfCopy) {
		this.selfCopy = selfCopy;
	}

	public boolean isCompress() {
		return compress;
	}

	public void setCompress(boolean compress) {
		this.compress = compress;
	}

}

