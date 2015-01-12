package com.victoroskar.hateup.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Account {
	@Id
	public Long account_id;
	public String username;
	public String location;
	@Temporal(TemporalType.DATE)
	public Date create_date;
	@OneToMany(mappedBy="owner")
	private List<Message> messages;
	
	public Account() {
	}

	public Account(String newUser, String newLocation) {
		this.username = newUser;
		this.location = newLocation;
		create_date = new Date();
	}

	public Long getAccount_id() {
		return this.account_id;
	}

	public void setAccount_id(Long newAccountID) {
		this.account_id = newAccountID;
	}

	public String getUser() {
		return this.username;
	}

	public void setUser(String newUser) {
		this.username = newUser;
	}

	public String getLocation() {
		return this.location;
	}

	public void setLocation(String newLocation) {
		this.location = newLocation;
	}

	public Date getCreate_date() {
		return this.create_date;
	}

	public void setCreate_date(Date newDate) {
		this.create_date = newDate;
	}
}
