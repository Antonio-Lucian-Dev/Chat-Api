package com.asusoftware.Chatapi.model;

// The chatId is generated by concatenating senderId_recipientId, for each conversation we persist two entries
// with the same chatId, one room, between sender and recipient and, the other one, between recipient and sender,
// to make sure that both users get the same chat Id.

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Setter
@Getter
@Entity
@Table(name = "chat_room")
public class ChatRoom {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "chat_id")
    private UUID chatId;

    @Column(name = "sender_id")
    private UUID senderId;

    @Column(name = "recipient_id")
    private UUID recipientId;
}
