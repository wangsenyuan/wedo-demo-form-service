package com.wedo.demo.domain.followup.repository;

import com.wedo.demo.domain.followup.entity.CustomerFollowUpEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerFollowUpRepository extends JpaRepository<CustomerFollowUpEntity, Long> {
}
