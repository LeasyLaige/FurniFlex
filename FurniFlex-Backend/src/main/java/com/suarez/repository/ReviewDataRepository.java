package com.suarez.repository;

import com.suarez.entity.ReviewData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewDataRepository extends JpaRepository<ReviewData, Integer> {
    List<ReviewData> findByProduct_Id(Integer productId);
}
