package com.suarez.serviceimpl;

import com.suarez.entity.InventoryData;
import com.suarez.model.Inventory;
import com.suarez.repository.InventoryDataRepository;
import com.suarez.service.InventoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class InventoryServiceImpl implements InventoryService {
    Logger logger = LoggerFactory.getLogger(InventoryServiceImpl.class);

    @Autowired
    InventoryDataRepository inventoryDataRepository;

    @Override
    public Inventory getInventoryById(int id) {
        logger.info("Getting inventory with id: " + id);
        Inventory inventory = null;
        Optional<InventoryData> inventoryData = inventoryDataRepository.findById(id);

            if (inventoryData.isPresent()) {
                inventory = new Inventory();
                inventory.setId(inventoryData.get().getId());
                inventory.setProduct(inventoryData.get().getProductData());
                inventory.setQuantity(inventoryData.get().getQuantity());
                inventory.setName(inventoryData.get().getName());
                inventory.setReservedQuantity(inventoryData.get().getReservedQuantity());
                logger.info("Successfully found inventory with id: " + id);
            } else {
                logger.warn("Inventory with id: " + id + " not found");
            }

        return inventory;
    }

    @Override
    public Inventory[] getAllInventories() {
        List<InventoryData> inventoryDataList = new ArrayList<>();
        List<Inventory> inventoryList = new ArrayList<>();

        inventoryDataRepository.findAll().forEach(inventoryDataList::add);
        Iterator<InventoryData> iterator = inventoryDataList.iterator();

        while (iterator.hasNext()) {
            InventoryData inventoryData = iterator.next();
            Inventory inventory = new Inventory();
            inventory.setId(inventoryData.getId());
            inventory.setProduct(inventoryData.getProductData());
            inventory.setQuantity(inventoryData.getQuantity());
            inventory.setName(inventoryData.getName());
            inventory.setReservedQuantity(inventoryData.getReservedQuantity());
            inventoryList.add(inventory);
        }

        Inventory[] inventoryArray = new Inventory[inventoryList.size()];
        for (int i = 0; i < inventoryList.size(); i++) {
            inventoryArray[i] = inventoryList.get(i);
        }

        return inventoryArray;
    }

    @Override
    public Inventory createInventory(Inventory inventory) {
        logger.info("Creating inventory with product: " + inventory.getProduct() + " and quantity: " + inventory.getQuantity());
        InventoryData inventoryData = new InventoryData();
        inventoryData.setProductData(inventory.getProduct());
        inventoryData.setQuantity(inventory.getQuantity());
        inventoryData.setName(inventory.getName());
        inventoryData.setReservedQuantity(inventory.getReservedQuantity());
        inventoryData = inventoryDataRepository.save(inventoryData);

        logger.info("Inventory created with id: " + inventoryData.getId());
        Inventory newInventory = new Inventory();
        newInventory.setId(inventoryData.getId());
        newInventory.setProduct(inventoryData.getProductData());
        newInventory.setQuantity(inventoryData.getQuantity());
        newInventory.setName(inventoryData.getName());
        newInventory.setReservedQuantity(inventoryData.getReservedQuantity());

        return newInventory;
    }

    @Override
    public Inventory updateInventory(Inventory inventory) {
        Inventory updatedInventory = null;
        int id = inventory.getId();
        Optional<InventoryData> inventoryData = inventoryDataRepository.findById(id);

        if (inventoryData.isPresent()) {
            InventoryData existingInventoryData = inventoryData.get();
            existingInventoryData.setProductData(inventory.getProduct());
            existingInventoryData.setQuantity(inventory.getQuantity());
            existingInventoryData.setName(inventory.getName());
            existingInventoryData.setReservedQuantity(inventory.getReservedQuantity());

            existingInventoryData = inventoryDataRepository.save(existingInventoryData);

            updatedInventory = new Inventory();
            updatedInventory.setId(existingInventoryData.getId());
            updatedInventory.setProduct(existingInventoryData.getProductData());
            updatedInventory.setQuantity(existingInventoryData.getQuantity());
            updatedInventory.setName(existingInventoryData.getName());
            updatedInventory.setReservedQuantity(existingInventoryData.getReservedQuantity());

            logger.info("Successfully updated inventory with id: " + id);
            return updatedInventory;
        } else {
            logger.warn("Inventory with id: " + id + " not found");
        }

        return updatedInventory;
    }

    @Override
    public void deleteInventory(int id) {
        Inventory inventory = null;
        logger.info("Deleting inventory with id: " + id);
        Optional<InventoryData> inventoryData = inventoryDataRepository.findById(id);

        if (inventoryData.isPresent()) {
            InventoryData inventoryDataToDelete = inventoryData.get();
            inventoryDataRepository.delete(inventoryDataToDelete);
            logger.info("Successfully deleted inventory with id: " + id);
        } else {
            logger.warn("Unable to delete inventory with id: " + id + " not found");
        }
    }
}
