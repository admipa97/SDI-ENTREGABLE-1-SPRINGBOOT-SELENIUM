package com.uniovi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uniovi.entities.Friendship;
import com.uniovi.entities.User;
import com.uniovi.repositories.FriendshipsRepository;

@Service
public class FriendshipService {

	@Autowired
	private FriendshipsRepository friendshipsRepository;

	public void addFriendship(User sender, User receiver) {
		Friendship friendship = new Friendship(sender, receiver);
		Friendship friendshipInverse = new Friendship(receiver, sender);
		friendshipsRepository.save(friendship);
		friendshipsRepository.save(friendshipInverse);
	}

}
