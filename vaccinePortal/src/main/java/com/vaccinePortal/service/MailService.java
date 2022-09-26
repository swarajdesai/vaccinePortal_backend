package com.vaccinePortal.service;

public interface MailService {
	void sendMail(String to , String msg , String subject) throws Exception;
}
