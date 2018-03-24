package com.uniovi.controllers;

import java.security.Principal;
import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.uniovi.entities.Request;
import com.uniovi.entities.User;
import com.uniovi.services.FriendshipService;
import com.uniovi.services.LoggerService;
import com.uniovi.services.RequestsService;
import com.uniovi.services.UsersService;

@Controller
public class RequestsController {
	@Autowired
	private UsersService usersService;

	@Autowired
	private RequestsService requestsService;

	@Autowired
	private FriendshipService friendshipService;

	private LoggerService loggerService = new LoggerService(this);

	@RequestMapping("/request/send/{id}")
	public String sendRequest(@PathVariable Long id, Principal principal) {
		User sender = usersService.getUserByEmail(principal.getName());
		User receiver = usersService.getUser(id);
		loggerService.info("Envío de petición de amistad de "
				+ sender.getEmail() + " a " + receiver.getEmail());
		requestsService.addRequest(new Request(sender, receiver));
		return "redirect:/user/list";
	}

	@RequestMapping("/request/requests")
	public String getRequests(Model model, Pageable pageable,
			Principal principal) {
		Page<User> users = new PageImpl<User>(new LinkedList<User>());
		String email = principal.getName();
		loggerService.info("Listado de peticiones realizado por " + email);
		users = usersService.getSendersByReceiverEmail(pageable, email);
		model.addAttribute("usersList", users.getContent());
		model.addAttribute("page", users);
		return "/request/requests";
	}

	@RequestMapping("/request/accept/{id}")
	public String acceptRequests(@PathVariable Long id, Principal principal) {
		User sender = usersService.getUser(id);
		User receiver = usersService.getUserByEmail(principal.getName());
		loggerService.info("Aceptación de petición de amistad de "
				+ sender.getEmail() + " por parte de " + receiver.getEmail());
		friendshipService.addFriendship(sender, receiver);
		requestsService.acceptRequest(sender, receiver);
		return "redirect:/request/requests";
	}

}