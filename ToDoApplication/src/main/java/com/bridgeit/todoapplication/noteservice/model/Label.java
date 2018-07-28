/**
 * 
 */
package com.bridgeit.todoapplication.noteservice.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 
 * 
 * @author Chaithra-Shenoy
 * @since Date 10-07-2018 <br>
 *        <p>
 *        <b>POJO Class having Label related information and method.</b>
 *        </p>
 */
@Document(collection="label")
public class Label {

	@Id
	private String id;
	private String name;
	private String user;
	private String note;
	/**
	 * @param id
	 * @param name
	 * @param user
	 * @param note
	 */
	public Label(String id, String name, String user, String note) {
		super();
		this.id = id;
		this.name = name;
		this.user = user;
		this.note = note;
	}
	
	/**
	 * 
	 */
	public Label() {
		super();
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the user
	 */
	public String getUser() {
		return user;
	}
	/**
	 * @param user the user to set
	 */
	public void setUser(String user) {
		this.user = user;
	}
	/**
	 * @return the note
	 */
	public String getNote() {
		return note;
	}
	/**
	 * @param note the note to set
	 */
	public void setNote(String note) {
		this.note = note;
	}
	
}
