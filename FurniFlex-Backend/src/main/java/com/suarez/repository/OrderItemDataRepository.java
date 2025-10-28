package com.suarez.repository;

import com.suarez.entity.OrderItemData;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemDataRepository extends CrudRepository<OrderItemData, Integer> {
}
