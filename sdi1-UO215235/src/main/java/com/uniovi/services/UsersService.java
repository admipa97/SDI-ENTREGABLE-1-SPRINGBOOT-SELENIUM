package com.uniovi.services;

import java.util.LinkedList;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.uniovi.entities.User;
import com.uniovi.repositories.UsersRepository;

@Service
public class UsersService {

	@Autowired
	private UsersRepository usersRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@PostConstruct
	public void init() {
	}

	public Page<User> getUsers(Pageable pageable, String email) {
		Page<User> users = new PageImpl<User>(new LinkedList<User>());
		users = usersRepository.findAll(pageable, email);
		return users;
	}

	public User getUser(Long id) {
		return usersRepository.findOne(id);
	}

	public void addUser(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		usersRepository.save(user);
	}

	public User getUserByEmail(String email) {
		return usersRepository.findByEmail(email);
	}

	public void deleteUser(Long id) {
		usersRepository.delete(id);
	}

	public Page<User> searchUsersByEmailAndName(Pageable pageable,
			String searchText, String email) {
		Page<User> users = new PageImpl<User>(new LinkedList<User>());
		searchText = "%" + searchText + "%";
		users = usersRepository.searchByEmailAndName(pageable, searchText,
				email);

		return users;
	}

	public Page<User> searchRequestsBySenderEmail(Pageable pageable,
			String senderEmail) {
		Page<User> users = new PageImpl<User>(new LinkedList<User>());
		users = usersRepository.searchBySenderEmail(pageable, senderEmail);

		return users;
	}

	public Page<User> getSendersByReceiverEmail(Pageable pageable,
			String email) {
		Page<User> users = new PageImpl<User>(new LinkedList<User>());
		users = usersRepository.findSendersByReceiverEmail(pageable, email);
		return users;
	}

	public Page<User> getFriendsByUserEmail(Pageable pageable, String email) {
		Page<User> users = new PageImpl<User>(new LinkedList<User>());
		users = usersRepository.findFriendsByUserEmail(pageable, email);
		return users;
	}
}
