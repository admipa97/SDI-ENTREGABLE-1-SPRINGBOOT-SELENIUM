package com.uniovi.entities;

import javax.persistence.*;

import java.util.*; //A collection that contains no duplicate elements

@Entity
public class User {
	@Id
	@GeneratedValue
	private long id;
	@Column(unique = true)
	private String email;
	private String name;
	private String lastName;
	private String role;

	private String password;
	@Transient // propiedad que no se almacena e la tabla.
	private String passwordConfirm;

	@Transient // No se mapea. Solo sirve para la vista
	private boolean receiveRequest;

	@OneToMany(mappedBy = "sender", cascade = CascadeType.ALL)
	private Set<Request> senders = new HashSet<>();

	@OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL)
	private Set<Request> receivers = new HashSet<>();

	@OneToMany(mappedBy = "friend", cascade = CascadeType.ALL)
	private Set<Friendship> friends = new HashSet<>();

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private Set<Friendship> iAmFriendOf = new HashSet<>();

	public User(String email, String name, String lastName) {
		super();
		this.email = email;
		this.name = name;
		this.lastName = lastName;
	}

	public User() {
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordConfirm() {
		return passwordConfirm;
	}

	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}

	public Set<Request> getSenders() {
		return senders;
	}

	public void setSenders(Set<Request> senders) {
		this.senders = senders;
	}

	public Set<Request> getReceivers() {
		return receivers;
	}

	public void setReceivers(Set<Request> receivers) {
		this.receivers = receivers;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		return true;
	}

	public Set<Friendship> getFriends() {
		return friends;
	}

	public void setFriends(Set<Friendship> friends) {
		this.friends = friends;
	}

	public Set<Friendship> getiAmFriendOf() {
		return iAmFriendOf;
	}

	public void setiAmFriendOf(Set<Friendship> iAmFriendOf) {
		this.iAmFriendOf = iAmFriendOf;
	}

	public long getId() {
		return id;
	}

	public boolean isReceiveRequest() {
		return receiveRequest;
	}

	public void setReceiveRequest(boolean receiveRequest) {
		this.receiveRequest = receiveRequest;
	}

}