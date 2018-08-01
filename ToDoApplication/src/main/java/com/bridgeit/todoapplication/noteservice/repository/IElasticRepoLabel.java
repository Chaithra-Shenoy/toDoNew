/**
 * 
 */
package com.bridgeit.todoapplication.noteservice.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.bridgeit.todoapplication.noteservice.model.Label;

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
public interface IElasticRepoLabel extends ElasticsearchRepository<Label, String> {

	/**
	 * @param name
	 *            <p>
	 *            This method is used to delete a label based on labelName provided.
	 *            </p>
	 */
	void deleteByName(String name);
}
