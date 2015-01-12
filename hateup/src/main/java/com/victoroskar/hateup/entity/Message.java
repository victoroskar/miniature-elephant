package com.victoroskar.hateup.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Message {
	@Id
	public Long idmessages;
	public String message;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "owner")
	public Account owner;
	@Temporal(TemporalType.DATE)
	public Date create_date;

	public Message() {
	}

	public Message(String newMessage, Account newOwner) {
		this.create_date = new Date();
		this.message = newMessage;
		this.owner = newOwner;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Long getIdmessages() {
		return idmessages;
	}

	public void setIdmessages(Long id) {
		this.idmessages = id;
	}

	public Account getOwner() {
		return this.owner;
	}

	public void setOwner(Account newOwner) {
		this.owner = newOwner;
	}

	public Date getCreate_Date() {
		return create_date;
	}

	public void setCreate_date(Date newDate) {
		this.create_date = newDate;
	}
}
