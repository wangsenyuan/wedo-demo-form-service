package com.wedo.demo.domain.fee.repository;

import com.wedo.demo.domain.fee.entity.FeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeeRepository extends JpaRepository<FeeEntity, Long> {
}
