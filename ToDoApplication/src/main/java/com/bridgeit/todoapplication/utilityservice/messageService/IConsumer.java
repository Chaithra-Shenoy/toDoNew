package com.bridgeit.todoapplication.utilityservice.messageService;

import javax.mail.MessagingException;

import com.bridgeit.todoapplication.userservice.model.MailDto;


/**
 * 
 * 
 * @author Chaithra-Shenoy
 * @since Date 10-07-2018 <br>
 *        <p>
 *        <b>IConsumer interface having one abstrct method.</b>
 *        </p>
 */
public interface IConsumer {

	public void recievedMessage(MailDto mailDTO) throws MessagingException;
}
