package com.wedo.demo.domain.process.repository;

import com.wedo.demo.domain.process.entity.ProcessInstanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProcessInstanceRepository extends JpaRepository<ProcessInstanceEntity, Long> {

    @Modifying(clearAutomatically = true)
    @Query("update ProcessInstanceEntity set processKey = :#{#entity.processKey}, state = :#{#entity.state} where id = :#{#entity.id}")
    int updateProcess(@Param("entity") ProcessInstanceEntity entity);
}
