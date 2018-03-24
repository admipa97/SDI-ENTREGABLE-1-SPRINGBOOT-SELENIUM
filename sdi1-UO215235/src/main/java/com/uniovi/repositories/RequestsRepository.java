package com.uniovi.repositories;

import org.springframework.data.repository.CrudRepository;

import com.uniovi.entities.Request;
import com.uniovi.entities.User;

public interface RequestsRepository extends CrudRepository<Request, Long> {

	Request findBySenderAndReceiver(User sender, User receiver);

}
