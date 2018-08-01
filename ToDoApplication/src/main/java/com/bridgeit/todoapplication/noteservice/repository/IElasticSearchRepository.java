/**
 * 
 */
package com.bridgeit.todoapplication.noteservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.bridgeit.todoapplication.noteservice.model.Note;

/**
 * 
 * 
 * @author Chaithra-Shenoy
 * @since Date 10-07-2018 <br>
 *        <p>
 *        <b>POJO Class having User related information and method.</b>
 *        </p>
 */
@Repository
public interface IElasticSearchRepository extends ElasticsearchRepository<Note, String> {

	/**
	 * @param string
	 * @return List<Note>
	 *         <p>
	 *         To find List of Note by providing noteId.If noteId is present it
	 *         returns List of Note otherwise returns null.
	 *         </p>
	 */
	public List<Note> findNoteByNoteId(String noteId);

	/**
	 * @param string
	 * @return List
	 *         <p>
	 *         To find a particular note by providing userId.If userId is valid then
	 *         it returns List of Note otherwise returns null.
	 *         </p>
	 */
	public List<Note> findByUser(String userId);

	/**
	 * @param note
	 * @return Optional
	 *         <p>
	 *         To find a Note by providing noteId.if the note id is present then it
	 *         returns corressponding note.otherwise it returns null.
	 *         </p>
	 */
	public Optional<Note> findByNoteId(String note);

	/**
	 * @param labelId
	 * @return List
	 *         <p>
	 *         To find a Note by passing Label information.if Label is present in
	 *         the note then it returns List of Note.otherwise it returns null.
	 *         </p>
	 */
	public List<Note> findByLabel(String labelId);
}
