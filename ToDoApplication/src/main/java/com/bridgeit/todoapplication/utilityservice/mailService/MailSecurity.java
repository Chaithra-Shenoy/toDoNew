/**
 * 
 */
package com.bridgeit.todoapplication.utilityservice.mailService;

import javax.mail.MessagingException;

import com.bridgeit.todoapplication.userservice.model.MailDto;


/**
 * 
 * 
 * @author Chaithra-Shenoy
 * @since Date 10-07-2018 <br>
 *        <p>
 *        <b>MailSecurity interface having one abstract method.</b>
 *        </p>
 */
public interface MailSecurity {

	public void sendEmail(MailDto mailDTO) throws MessagingException;

}
