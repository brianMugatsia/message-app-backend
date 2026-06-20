package com.brian.messageapp.repository;

import com.brian.messageapp.model.MessageRead;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MessageReadRepository extends JpaRepository<MessageRead, Long> {
    Optional<MessageRead> findByUser_IdAndMessage_Id(Long userId, Long messageId);
}
