package com.brian.messageapp.repository;

import com.brian.messageapp.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository
        extends JpaRepository<Message, Long> {
}