/**
 * 
 */
package com.bridgeit.todoapplication.utilityservice.mailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgeit.todoapplication.userservice.model.MailDto;
import com.bridgeit.todoapplication.utilityservice.messageService.ProducerImpl;


/**
 * 
 * 
 * @author Chaithra-Shenoy
 * @since Date 10-07-2018 <br>
 *        <p>
 *        <b>POJO Class having User related information and method.</b>
 *        </p>
 */
@Service
public class MailService {

	@Autowired
	private ProducerImpl producer;
	
	public void sendMail(String to, String body, String message) {
	
		MailDto mailDTO = new MailDto();
		mailDTO.setTo(to);
		mailDTO.setSubject(body);
		mailDTO.setBody(message);
	
		producer.produceMessage(mailDTO);
	}
}
