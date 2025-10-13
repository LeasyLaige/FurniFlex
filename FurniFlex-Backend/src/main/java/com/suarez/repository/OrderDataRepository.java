package com.suarez.repository;

import com.suarez.entity.OrderData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDataRepository extends JpaRepository<OrderData, Integer> {
}
