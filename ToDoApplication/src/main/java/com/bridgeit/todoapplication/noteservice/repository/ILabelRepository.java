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
 *        <b>POJO Class having User related information and method.</b>
 *        </p>
 */
@Repository
public interface ILabelRepository extends MongoRepository<Label, String>{

	/**
	 * @param name
	 */
	void deleteByName(String name);


}
