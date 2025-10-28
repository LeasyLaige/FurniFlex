package com.suarez.controller;

import com.suarez.model.ContactMessage;
import com.suarez.service.ContactService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/contact")
@CrossOrigin
public class ContactController {

    private final ContactService service;

    public ContactController(ContactService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ContactMessage> submit(@RequestBody ContactMessage message) {
        return ResponseEntity.ok(service.create(message));
    }
}
