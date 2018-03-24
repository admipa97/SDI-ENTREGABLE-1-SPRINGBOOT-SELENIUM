package com.uniovi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uniovi.entities.Request;
import com.uniovi.entities.User;
import com.uniovi.repositories.RequestsRepository;

@Service
public class RequestsService {

	@Autowired
	private RequestsRepository requestsRepository;

	public void addRequest(Request request) {
		requestsRepository.save(request);
	}

	public void acceptRequest(User sender, User receiver) {
		Request request = requestsRepository.findBySenderAndReceiver(sender,
				receiver);
		request.setAccepted(true);
		Request requestInverse = new Request(receiver, sender);
		requestInverse.setAccepted(true);
		requestsRepository.save(request);
		requestsRepository.save(requestInverse);
	}

}
