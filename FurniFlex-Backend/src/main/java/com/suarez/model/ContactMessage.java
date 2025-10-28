package com.suarez.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContactMessage {
    private Integer id;
    private String name;
    private String email;
    private String subject;
    private String message;
    private LocalDateTime created;
}
