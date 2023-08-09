package com.wedo.demo.domain.message.repository;

import com.wedo.demo.domain.message.entity.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MessageRepository extends JpaRepository<MessageEntity, Long> {
}
