package com.sigma.software.entities;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Entity
@Table(name = "messages")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "from_id", referencedColumnName = "id")
    private User sender;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "to_id", referencedColumnName = "id")
    private User recipient;

    private String body;

    @Column(name = "datetime")
    private ZonedDateTime dateTime;

    public Message() {
    }

    public Message(User sender, User recipient, String body, ZonedDateTime dateTime) {
        this.sender = sender;
        this.recipient = recipient;
        this.body = body;
        this.dateTime = dateTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getRecipient() {
        return recipient;
    }

    public void setRecipient(User recipient) {
        this.recipient = recipient;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public ZonedDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(ZonedDateTime dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", sender=" + sender.getUsername() +
                ", recipient=" + recipient.getUsername() +
                ", body='" + body + '\'' +
                ", dateTime=" + dateTime +
                '}';
    }
}
