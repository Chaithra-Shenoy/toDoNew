/**
 * 
 */
package com.bridgeit.todoapplication.noteservice.model;

import java.util.List;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * 
 * @author Chaithra-Shenoy
 * @since Date 18-07-2018 <br>
 *        <p>
 *        <b>POJO Class having Note related information and method.</b>
 *        </p>
 */
@Document
public class Note {

	@Id
	private String noteId;
	private String title;
	private String description;
	@ApiModelProperty(hidden = true)
	private String createdAt;
	@ApiModelProperty(hidden = true)
	private String updatedAt;
	private String user;
	@ApiModelProperty(hidden = true)
	private boolean trashStatus = false;
	@ApiModelProperty(hidden = true)
	private boolean pinStatus = false;
	private boolean isArchieve=false;
	
//	@DBRef(db="label")
	private List<Label> label;	

	

	/**
	 * @param noteId
	 * @param title
	 * @param description
	 * @param createdAt
	 * @param updatedAt
	 * @param user
	 * @param trashStatus
	 * @param pinStatus
	 * @param isArchieve
	 * @param labels
	 */
	public Note(String noteId, String title, String description, String createdAt, String updatedAt, String user,
			boolean trashStatus, boolean pinStatus, boolean isArchieve, List<Label> labels) {
		super();
		this.noteId = noteId;
		this.title = title;
		this.description = description;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.user = user;
		this.trashStatus = trashStatus;
		this.pinStatus = pinStatus;
		this.isArchieve = isArchieve;
		this.label = labels;
	}

	/**
	 * 
	 */
	public Note() {
	}

	/**
	 * @return the noteId
	 */
	public String getNoteId() {
		return noteId;
	}

	/**
	 * @param noteId
	 *            the noteId to set
	 */
	public void setNoteId(String noteId) {
		this.noteId = noteId;
	}

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
	 * @return the createdAt
	 */
	public String getCreatedAt() {
		return createdAt;
	}

	/**
	 * @param createdAt
	 *            the createdAt to set
	 */
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	/**
	 * @return the updatedAt
	 */
	public String getUpdatedAt() {
		return updatedAt;
	}

	/**
	 * @param updatedAt
	 *            the updatedAt to set
	 */
	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
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
	 * @return the trashStatus
	 */
	public boolean isTrashStatus() {
		return trashStatus;
	}

	/**
	 * @param trashStatus
	 *            the trashStatus to set
	 */
	public void setTrashStatus(boolean trashStatus) {
		this.trashStatus = trashStatus;
	}

	/**
	 * @return the pinStatus
	 */
	public boolean isPinStatus() {
		return pinStatus;
	}

	/**
	 * @param pinStatus
	 *            the pinStatus to set
	 */
	public void setPinStatus(boolean pinStatus) {
		this.pinStatus = pinStatus;
	}

	/**
	 * @return the isArchieve
	 */
	public boolean isArchieve() {
		return isArchieve;
	}

	/**
	 * @param isArchieve the isArchieve to set
	 */
	public void setArchieve(boolean isArchieve) {
		this.isArchieve = isArchieve;
	}

	/**
	 * @return the labels
	 */
	public List<Label> getLabel() {
		return label;
	}

	/**
	 * @param labels the labels to set
	 */
	public void setLabel(List<Label> labels) {
		this.label = labels;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Note [noteId=" + noteId + ", title=" + title + ", description=" + description + ", createdAt="
				+ createdAt + ", updatedAt=" + updatedAt + ", user=" + user + ", trashStatus=" + trashStatus
				+ ", pinStatus=" + pinStatus + ", isArchieve=" + isArchieve + ", label=" + label + "]";
	}
	

}
