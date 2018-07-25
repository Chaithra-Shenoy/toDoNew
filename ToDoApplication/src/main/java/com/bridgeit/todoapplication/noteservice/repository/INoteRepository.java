/**
 * 
 */
package com.bridgeit.todoapplication.noteservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.bridgeit.todoapplication.noteservice.model.Note;
import com.bridgeit.todoapplication.userservice.model.User;

/**
 * 
 * 
 * @author Chaithra-Shenoy
 * @since Date 18-07-2018 <br>
 *        <p>
 *        <b> INoteRepository interface which extends MongoRepository
 *        Interface</b>
 *        </p>
 */
@Repository
public interface INoteRepository extends MongoRepository<Note, String> {

	/**
	 * @param string
	 * @return List<Note>
	 */
	public List<Note> findNoteByNoteId(String string);

	/**
	 * @param string
	 * @return
	 */
	public List<Note> findByUser(String string);

	/**
	 * @param note
	 * @return
	 */
	public Optional<Note> findByNoteId(String note);
}
