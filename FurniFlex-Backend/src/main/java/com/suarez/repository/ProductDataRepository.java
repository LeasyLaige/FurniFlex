package com.suarez.repository;

import com.suarez.entity.ProductData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductDataRepository extends JpaRepository<ProductData, Integer> {
}
