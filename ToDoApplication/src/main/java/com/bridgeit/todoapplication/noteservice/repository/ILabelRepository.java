/**
 * 
 */
package com.bridgeit.todoapplication.noteservice.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.bridgeit.todoapplication.noteservice.model.Label;
import com.bridgeit.todoapplication.noteservice.model.LabelDto;

/**
 * 
 * 
 * @author Chaithra-Shenoy
 * @since Date 10-07-2018 <br>
 *        <p>
 *        <b>ILabelRepository interface having one abstract method.</b>
 *        </p>
 */
@Repository
public interface ILabelRepository extends MongoRepository<Label, String> {

	/**
	 * @param name
	 *            <p>
	 *            This method is used to delete a label based on labelName provided.
	 *            </p>
	 */
	void deleteByName(String name);

}
