/**
 * 
 */
package com.bridgeit.todoapplication.noteservice.controller;

import java.util.List;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgeit.todoapplication.noteservice.model.LabelDto;
import com.bridgeit.todoapplication.noteservice.model.Note;
import com.bridgeit.todoapplication.noteservice.model.NoteDto;
import com.bridgeit.todoapplication.noteservice.service.INoteService;
import com.bridgeit.todoapplication.userservice.exception.ToDoException;

import io.swagger.annotations.ApiOperation;

/**
 * 
 * 
 * @author Chaithra-Shenoy
 * @since Date 18-07-2018 <br>
 *        <p>
 *        <b>Note Controller Class.</b>
 *        </p>
 */
@RestController
@RequestMapping("/Note")
public class NoteController {
	public static final Logger logger = LoggerFactory.getLogger(NoteController.class);

	final String REQ_ID = "IN_USER";
	final String RES_ID = "OUT_USER";

	@Autowired
	INoteService service;

	/**
	 * @param note
	 * @param token
	 * @return ResponseEntity
	 *         </p>
	 *         To create a Note based on token passed.
	 *         </p>
	 */
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	@ApiOperation(value = "Create new note")
	public ResponseEntity<String> createNote(@RequestBody NoteDto note, @RequestHeader String token)
			throws ToDoException {
		logger.info(REQ_ID + " Creating note");
		System.out.println(token);
		// String token = req.getHeader("Aurtorization");
		service.createNote(note, token);
		logger.info(RES_ID + " Done Creating Note");
		return new ResponseEntity<>("Note created Successfully", HttpStatus.OK);

	}

	/**
	 * @param noteId
	 * @param token
	 * @return ResponseEntity
	 *         <p>
	 *         To move a note to Trash based on token and noteId.
	 *         </p>
	 * @throws ToDoException
	 */
	@RequestMapping(value = "/moveToTrash", method = RequestMethod.DELETE)
	@ApiOperation(value = "Move note to trash")
	public ResponseEntity<String> deleteNote(@RequestParam String noteId, @RequestHeader String token)
			throws ToDoException {
		logger.info(REQ_ID + " Deleting note to trash");
		service.delete(noteId, token);
		logger.info(RES_ID + " Note moved to trash");
		return new ResponseEntity<>("Note Moved to trash Successfully", HttpStatus.OK);
	}

	/**
	 * @param noteId
	 * @param note
	 * @param token
	 * @return ResponseEntity
	 *         <p>
	 *         To update a existing Note based on token and noteId.
	 *         </p>
	 * @throws ToDoException
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ApiOperation(value = "update exist note")
	public ResponseEntity<String> updateNote(@RequestParam("note id") String noteId, @RequestBody NoteDto note,
			@RequestHeader String token) throws ToDoException {
		logger.info(REQ_ID + " Updating note");
		service.update(noteId, note, token);
		logger.info(RES_ID + " Updated Note");
		return new ResponseEntity<>("Note Updated Successfully", HttpStatus.OK);
	}

	/**
	 * @param token
	 * @return ResponseEntity
	 *         <p>
	 *         To display a particular note based on token.
	 *         </p>
	 * @throws ToDoException
	 */
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	@ApiOperation(value = "Display all notes")
	public ResponseEntity<List<Note>> displayNote(@RequestHeader String token) throws ToDoException {
		logger.info(REQ_ID + " Display notes");
		List<Note> note = null;
		note = service.display(token);
		return new ResponseEntity<>(note, HttpStatus.OK);
	}

	/**
	 * @param noteId
	 * @param token
	 * @return ResponseEntity
	 *         <p>
	 *         To delete a note permanently from database.
	 *         </p>
	 * @throws ToDoException
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.DELETE)
	@ApiOperation(value = "delete note from database")
	public ResponseEntity<String> deleteNotePermanently(@RequestBody String noteId, @RequestHeader String token)
			throws ToDoException {
		logger.info(REQ_ID + " To Delete Note");
		service.deletePermanent(noteId, token);
		logger.info(RES_ID + " Note deleted");
		return new ResponseEntity<>("Note deleted Successfully", HttpStatus.OK);
	}

	/**
	 * @param noteId
	 * @param token
	 * @return ResponseEntity
	 * @throws ToDoException
	 *             <p>
	 *             To Restore the data from trash.
	 *             </p>
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/restoreNote", method = RequestMethod.PUT)
	@ApiOperation(value = "restore note from trash")
	public ResponseEntity<String> restoreFromTrash(@RequestParam("NoteId") String noteId, @RequestHeader String token)
			throws ToDoException {
		logger.info(REQ_ID + " To restore Note from trash");
		service.restoreFromTrash(noteId, token);
		logger.info(RES_ID + " Note Restored");
		return new ResponseEntity(" Successfully Restrored from trash", HttpStatus.OK);
	}

	/**
	 * @param noteId
	 * @param token
	 * @return ResponseEntity
	 * @throws ToDoException
	 *             <p>
	 *             To make a note as Important note.
	 *             </p>
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/impNote", method = RequestMethod.PUT)
	@ApiOperation(value = "pin note")
	public ResponseEntity<String> pinNote(@RequestParam("NoteId") String noteId, @RequestHeader String token)
			throws ToDoException {
		logger.info(REQ_ID + " To make a Note as Important");
		service.pinNote(noteId, token);
		logger.info(RES_ID + " Note is Pinned");
		return new ResponseEntity("Note set as important", HttpStatus.OK);
	}

	/**
	 * @param noteId
	 * @param token
	 * @return ResponseEntity
	 * @throws ToDoException
	 *             <p>
	 *             To make a note Archieve
	 *             </p>
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/ArchieveNote", method = RequestMethod.PUT)
	@ApiOperation(value = "Archieve note")
	public ResponseEntity<String> ArchieveNote(@RequestParam("NoteId") String noteId, @RequestHeader String token)
			throws ToDoException {
		logger.info(REQ_ID + " To make a Note as Important");
		service.ArchieveNote(noteId, token);
		logger.info(RES_ID + " Note is Pinned");
		return new ResponseEntity("Note is archieved", HttpStatus.OK);
	}

	/**
	 * @param token
	 * @param id
	 * @param reminderTime
	 * @return ResponseEntity
	 * @throws Exception
	 *             <p>
	 *             To make Reminder of particular note based on noteId given.
	 *             </P>
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/remindNote", method = RequestMethod.PUT)
	@ApiOperation(value = "Reminder note")
	public ResponseEntity<String> remindNote(@RequestHeader String token, @RequestParam("NoteId") String id,
			@RequestParam("time") String reminderTime) throws Exception {
		logger.info(REQ_ID + " Note Reminder");
		service.setReminder(token, id, reminderTime);
		return new ResponseEntity("remind is set", HttpStatus.OK);
	}

	/**
	 * @param labelDto
	 * @param token
	 * @return ResponseEntity
	 *         <p>
	 *         To create a label
	 *         </p>
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/createLabel", method = RequestMethod.POST)
	@ApiOperation(value = "Create new label")
	public ResponseEntity<String> createLabel(@RequestBody LabelDto labelDto, @RequestHeader String token) {
		logger.info(REQ_ID + " To create a label");
		service.createLabel(labelDto, token);
		return new ResponseEntity("Label created", HttpStatus.OK);
	}

	/**
	 * @param token
	 * @param name
	 * @return ResponseEntity
	 * @throws ToDoException
	 *             <p>
	 *             To delete a label based on label name
	 *             </p>
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/deleteLabel", method = RequestMethod.DELETE)
	@ApiOperation(value = "delete label from note and labellist")
	public ResponseEntity<String> deletelabel(@RequestHeader String token, @RequestParam("labelId") String id)
			throws ToDoException {
		logger.info(REQ_ID + " To delete a label");
		service.deleteLabel(id, token);
		return new ResponseEntity("Label deleted", HttpStatus.OK);
	}

	/**
	 * @param token
	 * @param id
	 * @param labelDto
	 * @return ResponseEntity
	 * @throws ToDoException
	 *             <p>
	 *             To update a existing label
	 *             </p>
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/updateLabel", method = RequestMethod.POST)
	@ApiOperation(value = "update a label")
	public ResponseEntity<String> updateLabel(@RequestHeader String token, @RequestParam("id") String id,
			@RequestBody LabelDto labelDto) throws ToDoException {
		logger.info(REQ_ID + " To update a label");
		service.updateLabel(id, labelDto, token);
		return new ResponseEntity("Label updated", HttpStatus.OK);
	}

	/**
	 * @param labelId
	 * @param note
	 * @param token
	 * @return
	 * @throws ToDoException
	 *             <p>
	 *             Add existing label to the note
	 *             </p>
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/addLabel", method = RequestMethod.POST)
	@ApiOperation(value = "add existing label to note")
	public ResponseEntity<String> addLabel(@RequestParam String labelId, @RequestParam("NoteId") String note,
			@RequestHeader String token) throws ToDoException {
		logger.info(REQ_ID + " To add a label to note");
		service.addLabel(labelId, token, note);
		return new ResponseEntity("Label added", HttpStatus.OK);
	}

	/**
	 * @param labelId
	 * @param token
	 * @return
	 * @throws ToDoException
	 *             <p>
	 *             delete a label from note and labelList
	 *             </p>
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/deleteLabelFromNote", method = RequestMethod.DELETE)
	@ApiOperation(value = "delete label from note")
	public ResponseEntity<String> deleteLabelToNote(@RequestParam("LabelId") String labelId,
			@RequestHeader String token) throws ToDoException {
		logger.info(REQ_ID + " To add a label to note");
		service.deleteLabelFromNote(labelId, token);
		return new ResponseEntity("Label removed", HttpStatus.OK);
	}

	/**
	 * @param labelId
	 * @param note
	 * @param token
	 * @return
	 * @throws ToDoException
	 * <p>
	 * To Rename a labelName in labelList and Note.
	 * </p>
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/rename", method = RequestMethod.POST)
	@ApiOperation(value = "rename the label name")
	public ResponseEntity<String> rename(@RequestParam String labelId, @RequestParam("New Label name") String note,
			@RequestHeader String token) throws ToDoException {
		logger.info(REQ_ID + " To add a label to note");
		service.renameLabel(labelId, token, note);
		return new ResponseEntity("Label renamed successfully", HttpStatus.OK);
	}

	/**
	 * @param labelName
	 * @param note
	 * @param token
	 * @return
	 * @throws ToDoException
	 * <p>
	 *    To add a new label to note and LabelList.
	 * </p>
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/addNewLabel", method = RequestMethod.POST)
	@ApiOperation(value = "add new label")
	public ResponseEntity<String> add(@RequestParam String labelName, @RequestParam("NoteId") String note,
			@RequestHeader String token) throws ToDoException {
		logger.info(REQ_ID + " To add a label to note");
		service.addNewLabel(note, labelName, token);
		return new ResponseEntity("Label added successfully", HttpStatus.OK);
	}

}
