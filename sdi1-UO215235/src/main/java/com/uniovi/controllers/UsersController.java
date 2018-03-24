package com.uniovi.controllers;

import java.security.Principal;
import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.uniovi.entities.Request;
import com.uniovi.entities.User;
import com.uniovi.services.LoggerService;
import com.uniovi.services.RolesService;
import com.uniovi.services.SecurityService;
import com.uniovi.services.UsersService;
import com.uniovi.validators.SignUpFormValidator;

@Controller
public class UsersController {
	@Autowired
	private UsersService usersService;

	@Autowired
	private SecurityService securityService;

	@Autowired
	private RolesService rolesService;

	@Autowired
	private SignUpFormValidator signUpFormValidator;

	private LoggerService loggerService = new LoggerService(this);

	@RequestMapping("/user/list")
	public String getListado(Model model, Pageable pageable,
			Principal principal,
			@RequestParam(value = "", required = false) String searchText) {
		Page<User> users = new PageImpl<User>(new LinkedList<User>());
		String email = principal.getName();
		loggerService.info("Listado de usuarios realizado por " + email);
		if (searchText != null && !searchText.isEmpty()) {
			users = usersService.searchUsersByEmailAndName(pageable, searchText,
					email);
		} else {
			users = usersService.getUsers(pageable, email);
		}
		User userAuthenticated = usersService
				.getUserByEmail(principal.getName());
		for (User user : users.getContent()) {
			Request request = new Request(userAuthenticated, user);
			request.setAccepted(true);
			Request requestInverse = new Request(user, userAuthenticated);
			requestInverse.setAccepted(true);
			if (userAuthenticated.getSenders().contains(request)
					|| userAuthenticated.getReceivers()
							.contains(requestInverse)) {
				user.setReceiveRequest(true);
			}
		}
		model.addAttribute("usersList", users.getContent());
		model.addAttribute("page", users);
		return "/user/list";
	}

	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public String signup(Model model) {
		loggerService.info("Intento de registro");
		model.addAttribute("user", new User());
		return "/signup";
	}

	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String signup(@Validated User user, BindingResult result,
			Model model) {

		signUpFormValidator.validate(user, result);
		if (result.hasErrors()) {
			loggerService.error("Registro fallido por mal inserción de datos");
			return "/signup";
		}

		loggerService.info("Registro correcto como " + user.getEmail());

		user.setRole(rolesService.getRoles()[0]);
		usersService.addUser(user);
		securityService.autoLogin(user.getEmail(), user.getPasswordConfirm());
		return "redirect:/user/list";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() {
		loggerService.info("Intento de logging");
		return "/login";
	}
}