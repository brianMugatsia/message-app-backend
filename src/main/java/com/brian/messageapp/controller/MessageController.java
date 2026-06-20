package com.brian.messageapp.controller;

import com.brian.messageapp.model.Message;
import com.brian.messageapp.model.MessageRead;
import com.brian.messageapp.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService service;

    @PostMapping
    public Message createMessage(
            @RequestBody Message message) {

        return service.create(message);
    }

    @GetMapping
    public List<Message> getMessages() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Message getMessage(
            @PathVariable Long id) {

        return service.getById(id);
    }

    @PostMapping("/{messageId}/read")
    public MessageRead markAsRead(
            @PathVariable Long messageId,
            @RequestParam Long userId) {

        return service.markAsRead(userId, messageId);
    }
}