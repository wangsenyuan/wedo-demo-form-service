package com.wedo.demo.domain.message.repository;

import com.wedo.demo.domain.message.MessageState;
import com.wedo.demo.domain.message.entity.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface MessageRepository extends JpaRepository<MessageEntity, Long> {

    @Modifying(clearAutomatically = true)
    @Query("update MessageEntity set state = :state where id = :id")
    void updateState(@Param("id") Long id, @Param("state") MessageState state);
}
