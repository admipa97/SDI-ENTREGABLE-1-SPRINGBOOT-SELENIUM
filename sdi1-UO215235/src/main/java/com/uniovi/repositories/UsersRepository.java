package com.uniovi.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.uniovi.entities.User;

public interface UsersRepository extends CrudRepository<User, Long> {

	User findByEmail(String email);

	@Query("SELECT u FROM User u WHERE u.email!=?1")
	Page<User> findAll(Pageable pageable, String email);

	@Query("SELECT u FROM User u WHERE (LOWER(u.email) LIKE LOWER(?1) OR "
			+ "LOWER(u.name) LIKE LOWER(?1)) AND u.email!=?2")
	Page<User> searchByEmailAndName(Pageable pageable, String searchText,
			String email);

	@Query("SELECT u FROM User u WHERE u.id NOT IN (SELECT r1.receiver.id FROM "
			+ "Request r1 WHERE r1.sender.email=?1 AND r1.accepted=1 "
			+ ") AND u.role='ROLE_USER' AND u.email!=?1")
	Page<User> searchBySenderEmail(Pageable pageable, String senderEmail);

	@Query("SELECT r.sender FROM Request r WHERE r.receiver.email=?1 and "
			+ "r.accepted=false")
	Page<User> findSendersByReceiverEmail(Pageable pageable,
			String receiverEmail);

	@Query("SELECT r.friend FROM Friendship r WHERE r.user.email=?1")
	Page<User> findFriendsByUserEmail(Pageable pageable, String email);

}
