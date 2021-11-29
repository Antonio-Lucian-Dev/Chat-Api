package com.asusoftware.Chatapi.service;

import com.asusoftware.Chatapi.model.ChatRoom;
import com.asusoftware.Chatapi.repository.ChatRoomRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;

    public Optional<UUID> getChatId(
            UUID senderId, UUID recipientId, boolean createIfNotExist) {

        return chatRoomRepository
                .findBySenderIdAndRecipientId(senderId, recipientId)
                .map(ChatRoom::getChatId)
                .or(() -> {
                    if(!createIfNotExist) {
                        return  Optional.empty();
                    }
                    var chatId =
                            String.format("%s_%s", senderId, recipientId);


                    ChatRoom senderRecipient = new ChatRoom();
                    senderRecipient.setChatId(chatId);
                    senderRecipient.setSenderId(senderId);
                    senderRecipient.setRecipientId(recipientId);

                    ChatRoom recipientSender = new ChatRoom();
                    recipientSender.setChatId(chatId);
                    recipientSender.setSenderId(senderId);
                    recipientSender.setRecipientId(recipientId);


                    chatRoomRepository.save(senderRecipient);
                    chatRoomRepository.save(recipientSender);

                    return Optional.of(chatId);
                });
    }
}
