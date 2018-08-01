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
import com.bridgeit.todoapplication.utilityservice.Messages;
import com.bridgeit.todoapplication.utilityservice.PreCondition;
import com.bridgeit.todoapplication.utilityservice.Utility;
import com.bridgeit.todoapplication.utilityservice.mailService.MailService;
import com.bridgeit.todoapplication.utilityservice.redisservice.IRedisRepository;

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

	@Autowired
	Messages messages;

	@Autowired
	PreCondition precondition;
	
	@Autowired
	IRedisRepository redisRepository;

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
		precondition.checkNotNull(registerDTO.getEmail(), messages.get("100"));
		Optional<User> optionalUser = userDao.findByEmail(registerDTO.getEmail());
		precondition.checkNotNull(registerDTO, messages.get("103"));
		if (optionalUser.isPresent()) {
			throw new ToDoException(messages.get("105"));
		}
		Utility.isValidateAllFields(registerDTO);
		String token = utility.createToken(registerDTO.getEmail());
		User userModel = model.map(registerDTO, User.class);
		userModel.setPassword(encoder.encode(registerDTO.getPassword()));
		userModel.setActivate(false);
		userDao.save(userModel);
		String body = messages.get("123");
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
		precondition.checkNotNull(loginDTO.getEmail(), messages.get("100"));
		precondition.checkNotNull(loginDTO.getPassword(),messages.get("101"));
		precondition.checkArgument(userDao.findByEmail(loginDTO.getEmail()), messages.get("103"));
		if (optionalUser.get().isActivate()) {
			String token = utility.createToken(loginDTO.getEmail());
			String userId = utility.parseJwt(token).getId();
			String tokenFromRedis = redisRepository.getToken(userId);
			if(tokenFromRedis==null)
			{
				throw new ToDoException("Unauthorised user");
			}
			if (!encoder.matches(loginDTO.getPassword(), optionalUser.get().getPassword())) {
				throw new ToDoException(messages.get("106"));
			}
			String body = messages.get("124");
			mailService.sendMail(loginDTO.getEmail(), body, token);
			return token;
		}
		logger.info(messages.get("107"));
		throw new ToDoException(messages.get("107"));
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
		precondition.checkNotNull(emailId, messages.get("100"));
		logger.info(optionalUser.get().getEmail());
		String token = utility.createToken(optionalUser.get().getEmail());
		logger.info(token);
		String body = messages.get("125");
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
		redisRepository.setToken(token);
		logger.info("token set into redis");
		precondition.checkNotNull(token, messages.get("102"));
		Optional<User> optionalUser = userDao.findByEmail(claim.getId());
		logger.info(optionalUser.get().getEmail());
		precondition.checkArgument(optionalUser, messages.get("103"));
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
		precondition.checkNotNull(resetPasswordDTO.getNewPassword(), messages.get("101"));
		precondition.checkNotNull(resetPasswordDTO.getPasswordConfirmation(),
				messages.get("101"));
		precondition.checkNotNull(token, messages.get("102"));
		logger.info(token);
		logger.info(claim.getId() + " " + claim.getSubject());
		if (!Utility.validatePassword(resetPasswordDTO.getNewPassword())) {
			logger.error(messages.get("106"));
			throw new ToDoException(messages.get("106"));
		}
		if (!Utility.isPasswordMatch(resetPasswordDTO.getNewPassword(), resetPasswordDTO.getPasswordConfirmation())) {
			logger.error(messages.get("108"));
			throw new ToDoException(messages.get("108"));
		}
		Optional<User> optionalUser = userDao.findById(claim.getId());
		precondition.checkArgument(userDao.findById(claim.getId()), messages.get("103"));
		User user = optionalUser.get();
		logger.info(user.getEmail());
		user.setPassword(encoder.encode(resetPasswordDTO.getNewPassword()));
		userDao.save(user);
	}
}
