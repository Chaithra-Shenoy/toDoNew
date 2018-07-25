/**
 * 
 */
package com.bridgeit.todoapplication.userservice.service;

import java.util.Optional;

import javax.mail.MessagingException;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bridgeit.todoapplication.userservice.dao.IUserRepository;
import com.bridgeit.todoapplication.userservice.exception.ToDoException;
import com.bridgeit.todoapplication.userservice.model.PasswordDto;
import com.bridgeit.todoapplication.userservice.model.RegisterDto;
import com.bridgeit.todoapplication.userservice.model.User;
import com.bridgeit.todoapplication.utilityservice.PreCondition;
import com.bridgeit.todoapplication.utilityservice.Utility;
import com.bridgeit.todoapplication.utilityservice.mailService.MailSecurity;
import com.bridgeit.todoapplication.utilityservice.mailService.MailService;
import com.bridgeit.todoapplication.utilityservice.messageService.ProducerImpl;

import io.jsonwebtoken.Claims;

/**
 * 
 * 
 * @author Chaithra-Shenoy
 * @since Date 10-07-2018 <br>
 *        <p>
 *        <b>Service Implementation class implements IUserService interface.</b>
 *        </p>
 */
@Service
public class UserServiceImplementation implements IUserService {

	@Autowired
	private IUserRepository userDao;

	@Autowired
	private PasswordEncoder encoder;

	@Autowired
	Utility utility;

	@Autowired
	private ModelMapper model;
	
	@Autowired
	private MailService mailService;

	public static final Logger logger = LoggerFactory.getLogger(UserServiceImplementation.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bridgelabz.todoapp.userservice.userservice.IUserService#registerUser(com.
	 * bridgelabz.todoapp.userservice.userdto.RegisterDto, java.lang.String)
	 */
	@Override
	public void registerUser(RegisterDto registerDTO, String uri) throws MessagingException, ToDoException {
		Optional<User> optionalUser = userDao.findByEmail(registerDTO.getEmail());
		PreCondition.checkNotNull(registerDTO, "Null value is not supported");

		if (optionalUser.isPresent()) {
			throw new ToDoException("User with this email already registered");
		}
		Utility.isValidateAllFields(registerDTO);
		String token = utility.createToken(registerDTO.getEmail());
		User userModel = model.map(registerDTO, User.class);
		userModel.setPassword(encoder.encode(registerDTO.getPassword()));
		userModel.setActivate(false);
		userDao.save(userModel);
		String body="Registration  Successfull";
		logger.info(token);
		mailService.sendMail(registerDTO.getEmail(), body, token);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bridgeit.todoapplication.userservice.service.IUserService#loginUser(com.
	 * bridgeit.todoapplication.userservice.dto.LoginDto, java.lang.String)
	 */
	@Override
	public String loginUser(RegisterDto loginDTO, String uri) throws ToDoException, MessagingException {
	
		Optional<User> optionalUser = userDao.findByEmail(loginDTO.getEmail());
		if(optionalUser.get().isActivate()) {
		PreCondition.checkNotNull(loginDTO.getEmail(), "Null value is not supported,Enter emailId");
		PreCondition.checkNotNull(loginDTO.getPassword(), "Null value is not supported, Enter Password");

		String token = utility.createToken(loginDTO.getEmail());
		PreCondition.checkArgument(userDao.findByEmail(loginDTO.getEmail()), "Incorrect Details Found");
		if (!encoder.matches(loginDTO.getPassword(), optionalUser.get().getPassword())) {
			throw new ToDoException("Invalid Password");
		}
		logger.info("password is correct");
		String body="Login Successfull";
		mailService.sendMail(loginDTO.getEmail(), token, body);
		return token;
		}
		logger.info("user is not active");
		throw new ToDoException("user is not active");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bridgeit.todoapplication.userservice.service.IUserService#forgotPassword(
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public void forgotPassword(String emailId, String uri) throws MessagingException, ToDoException {
		Optional<User> optionalUser = userDao.findByEmail(emailId);
		PreCondition.checkNotNull(emailId, "Null value is not supported,Enter emailId");
		logger.info(optionalUser.get().getEmail());
		String token = utility.createToken(optionalUser.get().getEmail());
		logger.info(token);
		String body="Forgot Password";
		mailService.sendMail(emailId, body, uri);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bridgeit.todoapplication.userservice.service.IUserService#
	 * setActivationStatus(java.lang.String)
	 */
	@Override
	public void setActivationStatus(String token) throws ToDoException {
		Claims claim = utility.parseJwt(token);
		PreCondition.checkNotNull(token, "Null value is not supported,Enter token");
		Optional<User> optionalUser = userDao.findByEmail(claim.getId());
		logger.info(optionalUser.get().getEmail());
	    PreCondition.checkArgument(optionalUser, "Incorrect Details Found");
		User user = optionalUser.get();
		user.setActivate(true);
		userDao.save(user);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bridgeit.todoapplication.userservice.service.IUserService#resetPassword(
	 * com.bridgeit.todoapplication.userservice.dto.PasswordDto, java.lang.String)
	 */
	@Override
	public void resetPassword(PasswordDto resetPasswordDTO, String token) throws ToDoException {
		Claims claim = utility.parseJwt(token);
		PreCondition.checkNotNull(resetPasswordDTO.getNewPassword(), "Null value is not supported, Enter Password");
		PreCondition.checkNotNull(resetPasswordDTO.getPasswordConfirmation(),
				"Null value is not supported, Enter Password");
		PreCondition.checkNotNull(token, "Null value is not supported,Enter token");
		logger.info(token);
		logger.info(claim.getId() + " " + claim.getSubject());
		if (!Utility.validatePassword(resetPasswordDTO.getNewPassword())) {
			logger.error("Invalid Password");
			throw new ToDoException("Invalid Password");
		}
		if (!Utility.isPasswordMatch(resetPasswordDTO.getNewPassword(), resetPasswordDTO.getPasswordConfirmation())) {
			logger.error(" Password Missmatch");
			throw new ToDoException("Password Missmatch");
		}
		Optional<User> optionalUser = userDao.findById(claim.getId());
		PreCondition.checkArgument(userDao.findById(claim.getId()), "Incorrect Details Found");
		User user = optionalUser.get();
		logger.info(user.getEmail());
		user.setPassword(encoder.encode(resetPasswordDTO.getNewPassword()));
		userDao.save(user);
	}


}
