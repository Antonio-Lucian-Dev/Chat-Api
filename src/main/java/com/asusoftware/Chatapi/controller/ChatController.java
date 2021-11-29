package com.asusoftware.Chatapi.controller;

import com.asusoftware.Chatapi.model.ChatMessage;
import com.asusoftware.Chatapi.model.ChatNotification;
import com.asusoftware.Chatapi.model.User;
import com.asusoftware.Chatapi.service.ChatMessageService;
import com.asusoftware.Chatapi.service.ChatRoomService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;
    private final ChatMessageService chatMessageService;
    private final ChatRoomService chatRoomService;

    // The @MessageMapping annotation ensures that, if a message is sent to /app/chat the processMessage method is called.
    // Note that, the configured application destination prefix /app is appended to the mapping.
    // This method, persists the message in MongoDB, then, calls convertAndSendToUser method to send the notification message to the user destination.

    @MessageMapping("/chat")
    public void processMessage(@Payload ChatMessage chatMessage) {
        var chatRoom = chatRoomService
                .getChatId(chatMessage.getSender(), chatMessage.getRecipient(), true).orElse(null);

        chatMessage.setChatRoom(chatRoom);

        ChatMessage saved = chatMessageService.save(chatMessage);

        // Creeaza notificarea pentru user
        ChatNotification chatNotification = new ChatNotification();
        chatNotification.setId(saved.getId());
        chatNotification.setSender(saved.getSender());

        // The convertAndSendToUser will append recipient id to /queue/messages, and also it will append the configured user
        // destination prefix /user at the beginning. The final destination will look like:
        //   /user/{recipientId}/queue/messages
        messagingTemplate.convertAndSendToUser(
                chatMessage.getRecipient().getId().toString(),"/queue/messages",
               chatNotification);
    }

    @GetMapping("/messages/{senderId}/{recipientId}/count")
    public ResponseEntity<Long> countNewMessages(
            @PathVariable UUID senderId,
            @PathVariable UUID recipientId) {

        return ResponseEntity
                .ok(chatMessageService.countNewMessages(senderId, recipientId));
    }

    @GetMapping("/messages/{senderId}/{recipientId}")
    public ResponseEntity<?> findChatMessages ( @PathVariable User senderId,
                                                @PathVariable User recipientId) {
        return ResponseEntity
                .ok(chatMessageService.findChatMessages(senderId, recipientId));
    }

    @GetMapping("/messages/{id}")
    public ResponseEntity<?> findMessage ( @PathVariable UUID id) {
        return ResponseEntity
                .ok(chatMessageService.findById(id));
    }
}
