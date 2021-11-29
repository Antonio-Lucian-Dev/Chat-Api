package com.asusoftware.Chatapi.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "chat_message")
public class ChatMessage {

        @Id
        @GeneratedValue
        private UUID id;

        @ManyToOne
        @JoinColumn(name="chat_id", nullable=false)
        private ChatRoom chatRoom;

        @ManyToOne
        @JoinColumn(name="sender_id", nullable=false)
        private User sender;

        @ManyToOne
        @JoinColumn(name="recipient_id", nullable=false)
        private User recipient;

        @Column(name = "content")
        private String content;

        @Column(name = "created_at")
        private LocalDateTime created_at;

        @Enumerated(EnumType.STRING)
        @Column(name = "message_status")
        private MessageStatus status;
}
