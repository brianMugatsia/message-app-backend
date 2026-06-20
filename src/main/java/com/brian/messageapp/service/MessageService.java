package com.brian.messageapp.service;

import com.brian.messageapp.model.Message;
import com.brian.messageapp.model.MessageRead;
import com.brian.messageapp.model.User;
import com.brian.messageapp.repository.MessageReadRepository;
import com.brian.messageapp.repository.MessageRepository;
import com.brian.messageapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository repository;
    private final UserRepository userRepository;
    private final MessageReadRepository messageReadRepository;

    // Create a new message
    public Message create(Message message) {
        message.setCreatedAt(LocalDateTime.now());
        return repository.save(message);
    }

    // Get all messages
    public List<Message> getAll() {
        return repository.findAll();
    }

    // Get message by ID
    public Message getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Message not found"));
    }

    // Mark a message as read by a user
    public MessageRead markAsRead(Long userId, Long messageId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        Message message = repository.findById(messageId)
                .orElseThrow(() ->
                        new RuntimeException("Message not found"));

        return messageReadRepository
                .findByUser_IdAndMessage_Id(userId, messageId)
                .orElseGet(() -> {

                    MessageRead messageRead = new MessageRead();

                    messageRead.setUser(user);
                    messageRead.setMessage(message);
                    messageRead.setRead(true);

                    return messageReadRepository.save(messageRead);
                });
    }
}