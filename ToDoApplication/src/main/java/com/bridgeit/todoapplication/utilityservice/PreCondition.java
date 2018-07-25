/**
 * 
 */
package com.bridgeit.todoapplication.utilityservice;

import java.util.Optional;

import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import com.bridgeit.todoapplication.userservice.exception.ToDoException;
import com.bridgeit.todoapplication.userservice.model.User;

/**
 * 
 * 
 * @author Chaithra-Shenoy
 * @since Date 10-07-2018 <br>
 *        <p>
 *        <b>PreCondition class having methods to check Exceptions</b>
 *        </p>
 */
@Component
public class PreCondition {
	PreCondition() {
	}

	/**
	 * @param reference
	 * @param errorMessage
	 * @return reference
	 * @throws ToDoException
	 * 
	 *             <p>
	 *             To check whether the reference is null or not. if null it throws
	 *             exception else it returns the same reference.
	 */
	public static <T> T checkNotNull(T reference, @Nullable Object errorMessage) throws ToDoException {
		if (reference.equals(null)) {
			throw new ToDoException(String.valueOf(errorMessage));
		}
		return reference;
	}

	/**
	 * @param existsById
	 * @param string
	 * @throws ToDoException
	 *             <p>
	 *             To check the given argument is present or not, if present it
	 *             returns same argument otherwise it throws Exception.
	 *             </p>
	 */
	public static <T> boolean checkArgument(boolean argument, @Nullable Object errorMessage) throws ToDoException {
		if (!argument) {
			throw new ToDoException(String.valueOf(errorMessage));
		}
		return argument;
	}

	/**
	 * @param <T>
	 * @param findByEmailId
	 * @param errorMessage
	 * @return 
	 * @throws ToDoException 
	 */
	public static <T> Optional<T> checkArgument(Optional<T> argument, String errorMessage) throws ToDoException {
		if (!argument.isPresent()) {
			throw new ToDoException(String.valueOf(errorMessage));
		}
		return argument;
	}

}
