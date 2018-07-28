/**
 * 
 */
package com.bridgeit.todoapplication.noteservice.model;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * 
 * @author Chaithra-Shenoy
 * @since Date 10-07-2018 <br>
 *        <p>
 *        <b>Label dto class having Label related information and method.</b>
 *        </p>
 */
public class LabelDto {

	
	private String id;
	private String name;
	
	
	
	/**
	 * 
	 */
	public LabelDto() {
		super();
	}
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "LabelDto [id=" + id + ", name=" + name + "]";
	}
}
