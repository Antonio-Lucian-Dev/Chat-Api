package com.asusoftware.Chatapi.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "chat_notification")
public class ChatNotification {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name="sender_id", nullable=false)
    private User sender;
}
