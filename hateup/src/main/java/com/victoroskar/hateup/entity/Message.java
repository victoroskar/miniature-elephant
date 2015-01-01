package com.victoroskar.hateup.entity;


import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Message {
    @Id Long idmessages;
    String message;
    @Temporal(TemporalType.DATE) 
    Date create_date;

    private Message() {}
    
    public Message(String newMessage)
    {
        this.create_date = new Date();
        this.message = newMessage;
    }
}
