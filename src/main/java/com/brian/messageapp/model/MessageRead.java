package com.brian.messageapp.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "message_reads")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageRead {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Message message;

    @Column(name = "is_read")
    private boolean read;
}