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
 *        <b>INoteService interface having four abstract methods.</b>
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
	 *             passed.
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
	 *             To update a record based on noteId and token passed.
	 *             </p>
	 */
	void update(String noteId, NoteDto note, String token) throws ToDoException;

	/**
	 * @param token
	 * @return 
	 * @throws ToDoException
	 *             <p>
	 *             to display the note id based on the token passed.
	 *             </p>
	 */
	List<Note> display(String token) throws ToDoException;

	/**
	 * @param noteId
	 * @param token
	 * <p>
	 * To delete a note permanently from database.
	 * </p>
	 * @throws ToDoException 
	 */
	public void deletePermanent(String noteId, String token) throws ToDoException;

	/**
	 * @param noteId
	 * @param token1
	 * @throws ToDoException
	 */
	void restoreFromTrash(String noteId, String token) throws ToDoException;

	/**
	 * @param noteId
	 * @param token
	 * @throws ToDoException 
	 */
	public void pinNote(String noteId, String token) throws ToDoException;

	/**
	 * @param note
	 * @param token
	 * @throws ToDoException
	 */
	void createNote(NoteDto note, String token) throws ToDoException;

	/**
	 * @param lableDto
	 * @param token
	 */
	void createLabel(LabelDto lableDto, String token);

	/**
	 * @param noteId
	 * @param token
	 * @throws ToDoException
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
	 */
	Note setReminder(String token, String id, String reminderTime) throws ToDoException, ParseException;

	/**
	 * @param name
	 * @param token
	 * @throws ToDoException 
	 */
	void deleteLabel(String name, String token) throws ToDoException;

	/**
	 * @param id
	 * @param lableDto
	 * @param token
	 * @throws ToDoException 
	 */
	void updateLabel(String id, LabelDto lableDto, String token) throws ToDoException;

	/**
	 * @param id
	 * @param note
	 * @param token
	 * @throws ToDoException
	 */
	void addLabeltoNote(String id, String note, String token) throws ToDoException;

	/**
	 * @param name
	 * @param note
	 * @param token
	 * @throws ToDoException
	 */
	void removeLabeltoNote(String name, String note, String token) throws ToDoException;



}
