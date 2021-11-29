package com.asusoftware.Chatapi.repository;

import com.asusoftware.Chatapi.model.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, UUID> {

    Optional<ChatRoom> findBySenderIdAndRecipientId(UUID senderId, UUID recipientId);
}
