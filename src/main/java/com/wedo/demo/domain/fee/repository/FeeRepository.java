package com.wedo.demo.domain.fee.repository;

import com.wedo.demo.domain.fee.entity.FeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FeeRepository extends JpaRepository<FeeEntity, Long> {
    @Modifying(clearAutomatically = true)
    @Query("update FeeEntity set processKey = :processKey where id = :id")
    void updateProcessKey(@Param("id") Long id, @Param("processKey") String processKey);
}
