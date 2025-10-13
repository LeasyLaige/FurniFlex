package com.suarez.service;

import com.suarez.model.Inventory;

public interface InventoryService {
    Inventory getInventoryById(int id);
    Inventory[] getAllInventories();
    Inventory updateInventory(Inventory inventory);
    Inventory createInventory(Inventory inventory);
    void deleteInventory(int id);
}
