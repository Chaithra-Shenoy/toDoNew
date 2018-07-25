/**
 * 
 */
package com.bridgeit.todoapplication.userservice.controller;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgeit.todoapplication.userservice.exception.ToDoException;
import com.bridgeit.todoapplication.userservice.model.PasswordDto;
import com.bridgeit.todoapplication.userservice.model.RegisterDto;
import com.bridgeit.todoapplication.userservice.model.ResponseDto;
import com.bridgeit.todoapplication.userservice.service.IUserService;

/**
 * 
 * 
 * @author Chaithra-Shenoy
 * @since Date 10-07-2018 <br>
 *        <p>
 *        <b>Controller Class Controls the flow of execution of program.</b>
 *        </p>
 */
@RestController
@RequestMapping("/user")
public class UserController {

	public static final Logger logger = LoggerFactory.getLogger(UserController.class);
	@Value(value = "${spring.mail.username}")
	private String mailid;

	@Autowired
	private IUserService userService;

	final String REQ_ID = "IN_USER";
	final String RES_ID = "OUT_USER";

	ResponseDto response = new ResponseDto();

	/**
	 * @param registerDTO
	 * @param request
	 * @return ResponseEntity
	 * @throws ToDoException
	 * @throws MessagingException
	 * 
	 *             <p>
	 *             Regesters a new user and stored user details in database
	 *             </p>
	 */
	@PostMapping("/register")
	public ResponseEntity<ResponseDto> registerUser(@RequestBody RegisterDto registerDTO, HttpServletRequest request) {
		logger.info("Creating User ");
		logger.info(REQ_ID + " " + registerDTO.getFirstName());
		try {
			userService.registerUser(registerDTO, request.getRequestURI());
		} catch (MessagingException | ToDoException e) {
			logger.error("Registration Unsuccessfull");
			response.setMessage(e.getMessage());
			response.setStatus(404);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
		ResponseDto response = new ResponseDto();
		response.setMessage("Registered successfully");
		response.setStatus(200);
		logger.info("Reponse message ", response);
		logger.info(RES_ID + " " + registerDTO.getFirstName());
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	/**
	 * @param loginDTO
	 * @param request
	 * @return ResponseEntity
	 * @throws ToDoException
	 * @throws MessagingException
	 *             <p>
	 *             User Login Method to login a regestered user.
	 *             </p>
	 */
	@PostMapping("/login")
	public ResponseEntity<?> loginUser(@RequestBody RegisterDto loginDTO, HttpServletRequest request) {
		logger.info("login  User");
		logger.info(REQ_ID + " " + loginDTO.getEmail());
		String token=null;
		try {
			token = userService.loginUser(loginDTO, request.getRequestURI());
		} catch (ToDoException | MessagingException e) {
			logger.error("Login Unsuccessfull");
			response.setMessage(e.getMessage());
			response.setStatus(404);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}

		response.setMessage("Login successfully done, And Token is :: "+token);
		response.setStatus(200);
		
		logger.info("Response message:", response);
		logger.info(RES_ID + " " + loginDTO.getEmail());
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	/**
	 * @param token
	 * @return ResponseEntity
	 * @throws ToDoException
	 * 
	 *             <p>
	 *             To Activate the Regestered user, once the user is activated his
	 *             status is changed to active user
	 *             </p>
	 */
	@GetMapping("/activation")
	public ResponseEntity<ResponseDto> activationUser(@RequestParam("token") String token) {
		logger.info("check the user activation");
		try {
			userService.setActivationStatus(token);
		} catch (ToDoException e) {
			logger.error("User activated Unsuccessfully");
			response.setMessage(e.getMessage());
			response.setStatus(404);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
		response.setMessage("User activated successfully");
		response.setStatus(200);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	/**
	 * @param emailId
	 * @param request
	 * @return ResponseEntityREQ_ID
	 * @throws ToDoException
	 * @throws TodoException
	 * @throws MessagingException
	 *             <p>
	 *             Forgot password to get the token to reset the password. By
	 *             passing valid email it generates a token and that token is sent
	 *             to user mail then user need to pass that token to reset password
	 *             to change existing password.
	 *             </p>
	 * 
	 */
	@PostMapping("/forgotPassword")
	public ResponseEntity<ResponseDto> forgotPassword(@RequestBody String emailId, HttpServletRequest request) throws ToDoException {
		logger.info(REQ_ID + " " + emailId);
		logger.info("Reset the password");
		logger.info(mailid);

		try {
			userService.forgotPassword(emailId, request.getRequestURI());
		} catch (MessagingException e) {
			logger.error("Invalid Password Exception");
			response.setMessage(e.getMessage());
			response.setStatus(404);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
		response.setMessage("send the user mailid to reset password");
		response.setStatus(200);
		logger.info(RES_ID + " " + emailId);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	/**
	 * @param resetPasswordDTO
	 * @param token
	 * @return ResponseEntity
	 * @throws TodoException
	 * 
	 *             <p>
	 *             Reset existing password by passing new password and confirm
	 *             password, here new password and confirm password matches then it
	 *             will reset the password of the particular user who passes the
	 *             valid token,otherwise exception occurs.
	 *             </p>
	 */
	@PostMapping("/resetPassword")
	public ResponseEntity<ResponseDto> resetPassword(@RequestBody PasswordDto resetPasswordDTO,
			@RequestParam("token") String token) {

		try {
			userService.resetPassword(resetPasswordDTO, token);
		} catch (ToDoException e) {
			logger.error("Password Reset Unsuccessfull");
			response.setMessage("reset the password Unsuccessfully");
			response.setStatus(404);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
		response.setMessage("reset the password successfully");
		response.setStatus(200);
		logger.info("Reset password done successfully");
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
}
