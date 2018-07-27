/**
 * 
 */
package com.bridgeit.todoapplication.noteservice.service;

import java.text.ParseException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.bridgeit.todoapplication.noteservice.model.Label;
import com.bridgeit.todoapplication.noteservice.model.LabelDto;
import com.bridgeit.todoapplication.noteservice.model.Note;
import com.bridgeit.todoapplication.noteservice.model.NoteDto;
import com.bridgeit.todoapplication.userservice.exception.ToDoException;

/**
 * 
 * 
 * @author Chaithra-Shenoy
 * @since Date 18-07-2018 <br>
 *        <p>
 *        <b>INoteService interface having abstract methods.</b>
 *        </p>
 */

public interface INoteService {

	/**
	 * @param noteId
	 * @param token
	 * @throws ToDoException
	 * 
	 *             <p>
	 *             To delete a particular record from database based on token
	 *             provided.
	 *             </p>
	 */
	void delete(String noteId, String token) throws ToDoException;

	/**
	 * @param noteId
	 * @param note
	 * @param token
	 * @throws ToDoException
	 * 
	 *             <p>
	 *             To update a record based on noteId and token provided.
	 *             </p>
	 */
	void update(String noteId, NoteDto note, String token) throws ToDoException;

	/**
	 * @param token
	 * @return
	 * @throws ToDoException
	 *             <p>
	 *             to display the note id based on the token provided.
	 *             </p>
	 */
	List<Note> display(String token) throws ToDoException;

	/**
	 * @param noteId
	 * @param token
	 *            <p>
	 *            To delete a note permanently from database.
	 *            </p>
	 * @throws ToDoException
	 */
	public void deletePermanent(String noteId, String token) throws ToDoException;

	/**
	 * @param noteId
	 * @param token1
	 * @throws ToDoException
	 *             <p>
	 *             To restore a note from the trash.
	 *             </p>
	 */
	void restoreFromTrash(String noteId, String token) throws ToDoException;

	/**
	 * @param noteId
	 * @param token
	 * @throws ToDoException
	 *             <p>
	 *             To make a note as important note
	 *             </p>
	 */
	public void pinNote(String noteId, String token) throws ToDoException;

	/**
	 * @param note
	 * @param token
	 * @throws ToDoException
	 *             <p>
	 *             To create a new note by passing note information and token.
	 *             </p>
	 */
	void createNote(NoteDto note, String token) throws ToDoException;

	/**
	 * @param lableDto
	 * @param token
	 *            <p>
	 *            To create a new label.
	 *            </p>
	 */
	void createLabel(LabelDto lableDto, String token);

	/**
	 * @param noteId
	 * @param token
	 * @throws ToDoException
	 *             <p>
	 *             To make a note as archieve.
	 *             </p>
	 */
	void ArchieveNote(String noteId, String token) throws ToDoException;

	/**
	 * @param token
	 * @param id
	 * @param reminderTime
	 * @return
	 * @throws ParseException
	 * @throws ToDoException
	 * @throws Exception
	 *             <p>
	 *             To keep a reminder to a particular note based on note id and
	 *             token passed. When the reminder date and time is set and on that
	 *             date and time a particular message is sent to user email.
	 *             </p>
	 */
	Note setReminder(String token, String id, String reminderTime) throws ToDoException, ParseException;


	/**
	 * @param id
	 * @param lableDto
	 * @param token
	 * @throws ToDoException
	 *             <p>
	 *             To update a existing label
	 *             </p>
	 */
	void updateLabel(String id, LabelDto lableDto, String token) throws ToDoException;


	/**
	 * @param name
	 * @param note
	 * @param token
	 * @throws ToDoException
	 *             <p>
	 *             To remove a label from the note.
	 *             </p>
	 */
	void removeLabeltoNote(String name, String note, String token) throws ToDoException;

	/**
	 * @param labelId
	 * @param token
	 * @param noteId
	 * @throws ToDoException
	 */
	void addLabel(String labelId, String token, String noteId) throws ToDoException;

	/**
	 * @param labelId
	 * @param token
	 * @throws ToDoException
	 */
	void deleteLabel(String labelId, String token) throws ToDoException;

	/**
	 * @param labelId
	 * @param token
	 * @throws ToDoException
	 */
	void deleteLabelFromNote(String labelId, String token) throws ToDoException;

	/**
	 * @param labelId
	 * @param token
	 * @param newLabelName
	 * @throws ToDoException
	 */
	void renameLabel(String labelId, String token, String newLabelName) throws ToDoException;

	/**
	 * @param note
	 * @param labelName
	 * @param token
	 * @throws ToDoException 
	 */
	void addNewLabel(String note, String labelName, String token) throws ToDoException;


}
