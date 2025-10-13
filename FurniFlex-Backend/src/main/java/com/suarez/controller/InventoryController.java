package com.suarez.controller;

import com.suarez.model.Inventory;
import com.suarez.service.InventoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class InventoryController {
    Logger logger = LoggerFactory.getLogger(InventoryController.class);

    @Autowired
    private InventoryService inventoryService;

    @GetMapping("api/inventory")
    public ResponseEntity<?> listInventories() {
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<?> response;

        try {
            Inventory[] inventories = inventoryService.getAllInventories();
            response = ResponseEntity.ok().headers(headers).body(inventories);
        } catch (Exception ex) {
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }

        return response;
    }

    @GetMapping("api/inventory/{id}")
    public ResponseEntity<?> get(@PathVariable final int id) {
        logger.info("Input inventory id >> " + id);
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<?> response;

        try {
            Inventory inventory = inventoryService.getInventoryById(id);
            response = ResponseEntity.ok(inventory);
        } catch (Exception ex) {
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }

        return response;
    }

    @PostMapping("api/inventory")
    public ResponseEntity<?> add(@RequestBody Inventory inventory) {
        logger.info("Input >> " + inventory.toString());
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<?> response;

        try {
            Inventory newInventory = inventoryService.createInventory(inventory);
            logger.info("created inventory >> " + newInventory.toString());
            response = ResponseEntity.ok().headers(headers).body(newInventory);
        } catch (Exception ex) {
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }

        return response;
    }

    @PutMapping("api/inventory/{id})")
    public ResponseEntity<?> update(@PathVariable final int id, @RequestBody Inventory inventory) {
        logger.info("Input >> " + inventory.toString());
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<?> response;

        try {
            Inventory updatedInventory = inventoryService.updateInventory(inventory);
            logger.info("updated inventory >> " + updatedInventory.toString());
            response = ResponseEntity.ok().headers(headers).body(updatedInventory);
        } catch (Exception ex) {
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }

        return response;
    }

    @DeleteMapping("api/inventory/{id})")
    public ResponseEntity<?> delete(@PathVariable final int id) {
        logger.info("Input inventory id >> " + id);
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<?> response;

        try {
            inventoryService.deleteInventory(id);
            response = ResponseEntity.ok().headers(headers).body("Successfully deleted inventory with id: " + id);
        } catch (Exception ex) {
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }

        return response;
    }
}
