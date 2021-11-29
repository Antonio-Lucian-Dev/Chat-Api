package com.asusoftware.Chatapi.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "chat_message")
public class ChatMessage {

        @Id
        @GeneratedValue
        private UUID id;

        @Column(name = "chat_id")
        private UUID chatId;

        @Column(name = "sender_id")
        private UUID senderId;

        @Column(name = "recipient_id")
        private UUID recipientId;

        @Column(name = "sender_name")
        private String senderName;

        @Column(name = "recipient_name")
        private String recipientName;

        @Column(name = "content")
        private String content;

        @Column(name = "send_at")
        private Date timestamp;

        @Enumerated(EnumType.STRING)
        @Column(name = "message_status")
        private MessageStatus status;
}
