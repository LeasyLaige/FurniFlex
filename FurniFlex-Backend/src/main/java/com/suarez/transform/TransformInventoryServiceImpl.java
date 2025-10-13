package com.suarez.transform;

import com.suarez.model.Inventory;
import com.suarez.entity.InventoryData;
import org.springframework.stereotype.Service;

@Service
public class TransformInventoryServiceImpl implements TransformInventoryService {
    @Override
    public InventoryData transformToEntity(Inventory inventory) {
        if (inventory == null) {
            return null;
        }
        InventoryData inventoryData = new InventoryData();
        inventoryData.setId(inventory.getId());
        inventoryData.setProductData(inventory.getProduct());
        inventoryData.setQuantity(inventory.getQuantity());
        inventoryData.setName(inventory.getName());
        inventoryData.setReservedQuantity(inventory.getReservedQuantity());
        return inventoryData;
    }

    @Override
    public Inventory transformToModel(InventoryData inventoryData) {
        if (inventoryData == null) {
            return null;
        }
        Inventory inventory = new Inventory();
        inventory.setId(inventoryData.getId());
        inventory.setProduct(inventoryData.getProductData());
        inventory.setQuantity(inventoryData.getQuantity());
        inventory.setName(inventoryData.getName());
        inventory.setReservedQuantity(inventoryData.getReservedQuantity());
        return inventory;
    }
}
