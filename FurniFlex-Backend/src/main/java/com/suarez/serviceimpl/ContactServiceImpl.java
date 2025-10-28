package com.suarez.serviceimpl;

import com.suarez.entity.ContactMessageData;
import com.suarez.model.ContactMessage;
import com.suarez.repository.ContactMessageDataRepository;
import com.suarez.service.ContactService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ContactServiceImpl implements ContactService {

    private final ContactMessageDataRepository repo;

    public ContactServiceImpl(ContactMessageDataRepository repo) {
        this.repo = repo;
    }

    @Override
    public ContactMessage create(ContactMessage message) {
        ContactMessageData cd = new ContactMessageData();
        cd.setName(message.getName());
        cd.setEmail(message.getEmail());
        cd.setSubject(message.getSubject());
        cd.setMessage(message.getMessage());
        cd.setCreated(LocalDateTime.now());
        ContactMessageData saved = repo.save(cd);
        return new ContactMessage(saved.getId(), saved.getName(), saved.getEmail(), saved.getSubject(), saved.getMessage(), saved.getCreated());
    }
}
