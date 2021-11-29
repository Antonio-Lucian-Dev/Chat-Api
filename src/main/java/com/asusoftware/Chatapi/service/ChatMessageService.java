package com.asusoftware.Chatapi.service;

import com.asusoftware.Chatapi.model.ChatMessage;
import com.asusoftware.Chatapi.model.MessageStatus;
import com.asusoftware.Chatapi.model.User;
import com.asusoftware.Chatapi.repository.ChatMessageRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.hibernate.Criteria;
import org.hibernate.sql.Update;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChatMessageService {
    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomService chatRoomService;
  //  private final MongoOperations mongoOperations;

    public ChatMessage save(ChatMessage chatMessage) {
        chatMessage.setStatus(MessageStatus.RECEIVED);
        chatMessageRepository.save(chatMessage);
        return chatMessage;
    }

    public long countNewMessages(UUID senderId, UUID recipientId) {
        return chatMessageRepository.countBySenderIdAndRecipientIdAndStatus(
                senderId, recipientId, MessageStatus.RECEIVED);
    }

    public List<ChatMessage> findChatMessages(User sender, User recipient) {
        var chatRoom = chatRoomService.getChatId(sender, recipient, false).orElse(null);

        List<ChatMessage> messages =  chatMessageRepository.findByChatId(chatRoom.getId());


        if(messages.size() > 0) {
            updateStatuses(sender.getId(), recipient.getId(), MessageStatus.DELIVERED);
        }

        return messages;
    }

    public ChatMessage findById(UUID id) {
        return chatMessageRepository
                .findById(id)
                .map(chatMessage -> {
                    chatMessage.setStatus(MessageStatus.DELIVERED);
                    return chatMessageRepository.save(chatMessage);
                })
                .orElseThrow(() ->
                        new ResourceNotFoundException("can't find message (" + id + ")"));
    }

    public void updateStatuses(UUID senderId, UUID recipientId, MessageStatus status) {
/*
        Query query = new Query(
                Criteria
                        .where("senderId").is(senderId)
                        .and("recipientId").is(recipientId));
        Update update = Update.update("status", status);
        mongoOperations.updateMulti(query, update, ChatMessage.class); */

        chatMessageRepository.findBySenderIdAndRecipientId(senderId, recipientId).setStatus(status);
    }
}