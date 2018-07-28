/**
 * 
 */
package com.bridgeit.todoapplication.noteservice.model;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * 
 * @author Chaithra-Shenoy
 * @since Date 18-07-2018 <br>
 *        <p>
 *        <b>Note Dto class.</b>
 *        </p>
 */
@SuppressWarnings("serial")
public class NoteDto implements Serializable {

	private String title;
	private String description;
	private String user;
	private List<LabelDto> label;

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the userId
	 */
	public String getUser() {
		return user;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUser(String user) {
		this.user = user;
	}

	/**
	 * @return the label
	 */
	public List<LabelDto> getLabel() {
		return label;
	}

	/**
	 * @param label
	 *            the label to set
	 */
	public void setLabel(List<LabelDto> label) {
		this.label = label;
	}

}
