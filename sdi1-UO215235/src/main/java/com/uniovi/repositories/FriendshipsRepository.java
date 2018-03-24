package com.uniovi.repositories;

import org.springframework.data.repository.CrudRepository;

import com.uniovi.entities.Friendship;

public interface FriendshipsRepository
		extends CrudRepository<Friendship, Long> {

}
