package com.asusoftware.Chatapi.service;

import com.asusoftware.Chatapi.model.ChatRoom;
import com.asusoftware.Chatapi.model.User;
import com.asusoftware.Chatapi.repository.ChatRoomRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;

    public Optional<ChatRoom> getChatId(
            User sender, User recipient, boolean createIfNotExist) {

        return chatRoomRepository
                // Cauta chatul folosing id la useri
                .findBySenderIdAndRecipientId(sender.getId(), recipient.getId())
                .or(() -> {  // Daca nu, creeaza un nou chat room
                    if(!createIfNotExist) {
                        return  Optional.empty();
                    }

                    UUID chatId = UUID.randomUUID();

                    // Creez chat room-ul
                    ChatRoom newChatRoom = new ChatRoom();
                    newChatRoom.setId(chatId);
                    newChatRoom.setSender(sender);
                    newChatRoom.setRecipient(recipient);

                    chatRoomRepository.save(newChatRoom);

                    return Optional.of(newChatRoom);
                });
    }
}
