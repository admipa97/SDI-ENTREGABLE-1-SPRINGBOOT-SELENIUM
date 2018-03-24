package com.uniovi.controllers;

import java.security.Principal;
import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.uniovi.entities.User;
import com.uniovi.services.LoggerService;
import com.uniovi.services.UsersService;

@Controller
public class FriendshipsController {

	@Autowired
	private UsersService usersService;

	private LoggerService loggerService = new LoggerService(this);

	@RequestMapping("/friendship/friendships")
	public String getFriendships(Model model, Pageable pageable,
			Principal principal) {
		Page<User> users = new PageImpl<User>(new LinkedList<User>());
		String email = principal.getName();
		loggerService.info("Listado de amigos realizado por " + email);
		users = usersService.getFriendsByUserEmail(pageable, email);
		model.addAttribute("usersList", users.getContent());
		model.addAttribute("page", users);
		return "/friendship/friendships";
	}
}