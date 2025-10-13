package com.suarez.transform;

import com.suarez.model.Inventory;
import com.suarez.entity.InventoryData;

public interface TransformInventoryService {
    InventoryData transformToEntity(Inventory inventory);
    Inventory transformToModel(InventoryData inventoryData);
}
