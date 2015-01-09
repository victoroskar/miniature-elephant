package com.victoroskar.hateup.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Message {
	@Id
	Long idmessages;
	public String message;
	String owner;
	@Temporal(TemporalType.DATE)
	Date create_date;

	public Message() {
	}

	public Message(String newMessage, String newOwner) {
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
	
	public String getOwner() {
		return owner;
	}

	public void setOwner(String newOwner) {
		this.owner = newOwner;
	}
	
	public Date getCreate_Date() {
		return create_date;
	}

	public void setCreate_date(Date newDate) {
		this.create_date = newDate;
	}
}
