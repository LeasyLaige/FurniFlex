package com.suarez.repository;

import com.suarez.entity.ContactMessageData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactMessageDataRepository extends JpaRepository<ContactMessageData, Integer> {
}
