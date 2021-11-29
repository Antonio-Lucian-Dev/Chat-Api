package com.asusoftware.Chatapi.repository;

import com.asusoftware.Chatapi.model.ChatMessage;
import com.asusoftware.Chatapi.model.MessageStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, UUID> {

    long countBySenderIdAndRecipientIdAndStatus(
            UUID senderId, UUID recipientId, MessageStatus status);

    @Query("SELECT c FROM ChatMessage c WHERE c.id =:chatId")
    List<ChatMessage> findByChatId(UUID chatId);

    ChatMessage findBySenderIdAndRecipientId(UUID senderId, UUID recipientId);
}
