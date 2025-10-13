package com.suarez.repository;

import com.suarez.entity.InventoryData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryDataRepository extends JpaRepository<InventoryData, Integer> {
}
