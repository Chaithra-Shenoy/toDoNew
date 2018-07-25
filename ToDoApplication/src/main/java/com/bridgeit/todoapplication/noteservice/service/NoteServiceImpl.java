/**
 * 
 */
package com.bridgeit.todoapplication.noteservice.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.bridgeit.todoapplication.noteservice.model.Label;
import com.bridgeit.todoapplication.noteservice.model.LabelDto;
import com.bridgeit.todoapplication.noteservice.model.Note;
import com.bridgeit.todoapplication.noteservice.model.NoteDto;
import com.bridgeit.todoapplication.noteservice.repository.ILabelRepository;
import com.bridgeit.todoapplication.noteservice.repository.INoteRepository;
import com.bridgeit.todoapplication.userservice.dao.IUserRepository;
import com.bridgeit.todoapplication.userservice.exception.ToDoException;
import com.bridgeit.todoapplication.userservice.model.User;
import com.bridgeit.todoapplication.utilityservice.PreCondition;
import com.bridgeit.todoapplication.utilityservice.Utility;
import com.bridgeit.todoapplication.utilityservice.mailService.MailService;

import io.jsonwebtoken.Claims;

/**
 * 
 * 
 * @author Chaithra-Shenoy
 * @since Date 18-07-2018 <br>
 *        <p>
 *        <b>NoteService implementation class having implementation for the
 *        methods declared in INoteService interface..</b>
 *        </p>
 */
@Service
public class NoteServiceImpl implements INoteService {
	public static final Logger logger = LoggerFactory.getLogger(NoteServiceImpl.class);

	@Autowired
	INoteRepository repository;

	@Autowired
	ILabelRepository labelRepository;

	@Autowired
	private IUserRepository userRepository;

	@Autowired
	Utility util;

	@Autowired
	private ModelMapper model;

	@Autowired
	MailService mail;

	@Autowired
	private IUserRepository userDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bridgeit.todoapplication.noteservice.service.INoteService#createNote(com.
	 * bridgeit.todoapplication.noteservice.model.Note, java.lang.String)
	 */
	@Override
	public void createNote(NoteDto note, String token) throws ToDoException {
		PreCondition.checkNotNull(note.getDescription(), "Null value is not supported, Enter description");
		PreCondition.checkNotNull(token, "Null value is not supported, Enter token");

		Claims data = util.parseJwt(token);
		logger.info(data.getId());
		Optional<User> user = userRepository.findByEmail(data.getId());
		Note noteModel = model.map(note, Note.class);
		noteModel.setUser(user.get().getId());
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String createdDate = simpleDateFormat.format(new Date());
		noteModel.setCreatedAt(createdDate);
		noteModel.setUpdatedAt(createdDate);
		repository.save(noteModel);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bridgeit.todoapplication.noteservice.service.INoteService#delete(java.
	 * lang.String, java.lang.String)
	 */
	@Override
	public void delete(String noteId, String token) throws ToDoException {
		PreCondition.checkNotNull(token, "Null value is not supported, Enter token");
		PreCondition.checkArgument(repository.existsById(noteId), "The entered NoteId is not present");
		Note note = repository.findById(noteId).get();
		logger.info(note.toString());
		note.setTrashStatus(true);
		repository.save(note);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bridgeit.todoapplication.noteservice.service.INoteService#update(java.
	 * lang.String, com.bridgeit.todoapplication.noteservice.model.Note,
	 * java.lang.String)
	 */
	@Override
	public void update(String noteId, NoteDto note, String token) throws ToDoException {
		PreCondition.checkNotNull(token, "Null value is not supported, Enter token");
		PreCondition.checkNotNull(note.getDescription(), "Null value is not supported, Enter description");
		PreCondition.checkArgument(repository.existsById(noteId), "The entered NoteId is not present");
		Claims data = util.parseJwt(token);
		Optional<User> user = userRepository.findByEmail(data.getId());
		Optional<Note> note1 = repository.findById(noteId);
		logger.info(note1.get().getDescription());
		if (note1.get().isTrashStatus()) {
			logger.info("Note is present in trash");
			throw new ToDoException("Note is present in trash");
		}
		Note noteModel = model.map(note, Note.class);
		noteModel.setNoteId(noteId);
		noteModel.setUser(user.get().getId());
		noteModel.setCreatedAt(note1.get().getCreatedAt());
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		noteModel.setUpdatedAt(simpleDateFormat.format(new Date()));
		repository.save(noteModel);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bridgeit.todoapplication.noteservice.service.INoteService#display(java.
	 * lang.String)
	 */
	@Override
	public List<Note> display(String token) throws ToDoException {
		/*
		 * PreCondition.checkNotNull(token, "Null value is not supported, Enter token");
		 * Claims data = util.parseJwt(token); logger.info(data.getId()); Optional<User>
		 * user = userRepository.findById(data.getId());
		 * logger.info(user.get().getEmail()); List<Note> note = repository.findAll();
		 * return note;
		 */
		List<Note> list = new ArrayList<>();
		List<Note> modifiedList = new ArrayList<>();
		PreCondition.checkNotNull(token, "Token cannot be empty");
		Claims email = util.parseJwt(token);
		Optional<User> user = userRepository.findByEmail(email.getId());
		list = repository.findAll();
		for (Note n : list) {
			if (n.isPinStatus() && !n.isTrashStatus() && !n.isArchieve()) {
				modifiedList.add(n);
			}
		}
		for (Note n : list) {
			if (!n.isArchieve() && !n.isTrashStatus() && !n.isPinStatus()) {
				modifiedList.add(n);
			}
		}
		for (Note n : list) {
			if (n.isArchieve() && !n.isTrashStatus()) {
				modifiedList.add(n);
			}
		}

		return modifiedList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bridgeit.todoapplication.noteservice.service.INoteService#deletePermanent
	 * (java.lang.String, java.lang.String)
	 */
	@Override
	public void deletePermanent(String noteId, String token) throws ToDoException {
		PreCondition.checkNotNull(token, "Null value is not supported, Enter token");
		PreCondition.checkNotNull(noteId, "Null value is not supported, Enter noteId");
		repository.deleteById(noteId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bridgeit.todoapplication.noteservice.service.INoteService#
	 * restoreFromTrash(java.lang.String, java.lang.String)
	 */
	@Override
	public void restoreFromTrash(String noteId, String token) throws ToDoException {
		PreCondition.checkNotNull(token, "Null value is not supported, Enter token");
		PreCondition.checkNotNull(noteId, "Null value is not supported, Enter noteId");
		PreCondition.checkArgument(repository.existsById(noteId), "The entered NoteId is not present");
		Note note = repository.findById(noteId).get();
		if (note.isTrashStatus()) {
			note.setTrashStatus(false);
			repository.save(note);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bridgeit.todoapplication.noteservice.service.INoteService#pinNote(java.
	 * lang.String, java.lang.String)
	 */
	@Override
	public void pinNote(String noteId, String token) throws ToDoException {
		PreCondition.checkNotNull(token, "Null value is not supported, Enter token");
		PreCondition.checkNotNull(noteId, "Null value is not supported,Enter noteId");
		PreCondition.checkArgument(repository.existsById(noteId), "The entered NoteId is not present");
		Note note = repository.findById(noteId).get();
		logger.info(note.toString());
		if (!note.isTrashStatus()) {
			note.setPinStatus(true);
		}
		repository.save(note);
	}

	@Override
	public void ArchieveNote(String noteId, String token) throws ToDoException {
		PreCondition.checkNotNull(token, "Null value is not supported, Enter token");
		PreCondition.checkNotNull(noteId, "Null value is not supported,Enter noteId");
		PreCondition.checkArgument(repository.existsById(noteId), "The entered NoteId is not present");
		Note note = repository.findById(noteId).get();
		logger.info(note.toString());
		if (!note.isTrashStatus()) {
			note.setArchieve(true);
		}
		repository.save(note);
	}

	@Override
	public Note setReminder(String token, String id, String reminderTime) throws ParseException, ToDoException {
		Optional<Note> note = PreCondition.checkNotNull(repository.findById(id), "No notes found");
		Timer timer = null;
		if (note.isPresent()) {
			Date reminder = new SimpleDateFormat("yyyy/MM/dd HH:mm").parse(reminderTime);
			long timeDifference = reminder.getTime() - new Date().getTime();
			timer = new Timer();
			Claims claim = util.parseJwt(token);
			Optional<User> optionalUser = userDao.findByEmail(claim.getId());
			timer.schedule(new TimerTask() {
				@Override
				public void run() {
					System.out.println("Reminder task:" + note.toString());
					mail.sendMail(optionalUser.get().getEmail(), "reminder started", "Remind Note");
				}
			}, timeDifference);
		}
		return note.get();
	}

	/**
	 * @param lableDto
	 * @param token
	 */
	@Override
	public void createLabel(LabelDto lableDto, String token) {
		Claims data = util.parseJwt(token);
		logger.info(data.getId());
		Optional<User> user = userRepository.findByEmail(data.getId());
		Label labelModel = model.map(lableDto, Label.class);
		labelModel.setUser(user.get().getId());
		labelRepository.save(labelModel);
	}

	@Override
	public void deleteLabel(String name, String token) throws ToDoException {
		PreCondition.checkArgument(labelRepository.existsById(name), "The entered Label name  is not present");
		labelRepository.deleteByName(name);
	}

	@Override
	public void updateLabel(String id, LabelDto lableDto, String token) throws ToDoException {
		Claims data = util.parseJwt(token);
		logger.info(data.getId());
		Optional<User> user = userRepository.findByEmail(data.getId());
		PreCondition.checkArgument(labelRepository.existsById(id), "The entered Label name  is not present");
		Label labelModel = model.map(lableDto, Label.class);
		labelModel.setId(id);
		labelModel.setUser(user.get().getId());
		labelRepository.save(labelModel);

	}

	@Override
	public void addLabeltoNote(String name, String note, String token) throws ToDoException {
		Claims data = util.parseJwt(token);
		logger.info(data.getId());
		Optional<User> user = userRepository.findByEmail(data.getId());
		Optional<Note> note1 = repository.findByNoteId(note);
		List<Note> noteList = repository.findByUser(user.get().getId());
		System.out.println(noteList);
		LabelDto label = new LabelDto();

		PreCondition.checkArgument(repository.existsById(note), "The entered NoteId is not present");
		for (Note n : noteList) {
			System.out.println(n.getNoteId());

			if (n.getNoteId().equals(note)) {
				label.setName(name);
				Label labelmap = model.map(label, Label.class);
				labelRepository.save(labelmap);
				System.out.println(label);
				Note noteLabel = model.map(label, Note.class);
				n.getLabel().add(label);
				repository.save(n);
			}
		}
	}

	@Override
	public void removeLabeltoNote(String name, String note, String token) throws ToDoException {
		Claims data = util.parseJwt(token);
		logger.info(data.getId());
		Optional<User> user = userRepository.findByEmail(data.getId());
		Optional<Note> note1 = repository.findByNoteId(note);
		List<Note> noteList = repository.findByUser(user.get().getId());
		System.out.println(noteList);
		LabelDto label = new LabelDto();

		PreCondition.checkArgument(repository.existsById(note), "The entered NoteId is not present");
		for (Note n : noteList) {
			System.out.println(n.getNoteId());

			if (n.getNoteId().equals(note)) {
				System.out.println(label);
				
				n.getLabel().remove(label);
				Note noteLabel = model.map(label, Note.class);
				logger.info(label.getName());
				repository.save(n);
				logger.info(n.getLabel().toString());
			}
		}
	}
	
}
