package com.asusoftware.Chatapi.repository;

import com.asusoftware.Chatapi.model.ChatMessage;
import com.asusoftware.Chatapi.model.MessageStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, UUID> {

    long countBySenderIdAndRecipientIdAndStatus(
            String senderId, String recipientId, MessageStatus status);

    List<ChatMessage> findByChatId(String chatId);

    ChatMessage findBySenderIdAndRecipientId(UUID senderId, UUID recipientId);
}
