package com.clubreconnect.service;

import com.clubreconnect.entity.Message;
import com.clubreconnect.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;

    public Message sendMessage(Message message) {
        message.setTimestamp(LocalDateTime.now());
        return messageRepository.save(message);
    }

    public List<Message> getMessagesForClub(Long clubId) {
        return messageRepository.findByClubIdOrderByTimestampAsc(clubId);
    }
}
