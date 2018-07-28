/**
 * 
 */
package com.bridgeit.todoapplication.utilityservice.messageService;

import com.bridgeit.todoapplication.userservice.model.MailDto;

/**
 * 
 * 
 * @author Chaithra-Shenoy
 * @since Date 10-07-2018 <br>
 *        <p>
 *        <b>IProducer interface having one abstract method.</b>
 *        </p>
 */
public interface IProducer {
	/**
	 * @param mailDTO
	 */
	public void produceMessage(MailDto mailDTO);
}
