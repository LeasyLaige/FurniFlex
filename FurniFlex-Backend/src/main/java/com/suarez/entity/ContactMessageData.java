package com.suarez.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "contact_message")
public class ContactMessageData {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "contact_message_seq")
    @SequenceGenerator(name = "contact_message_seq", sequenceName = "contact_message_SEQ", allocationSize = 1)
    private Integer id;

    private String name;
    private String email;
    private String subject;

    @Column(length = 4000)
    private String message;

    private LocalDateTime created;
}
