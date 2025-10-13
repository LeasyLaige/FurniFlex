package com.suarez.controller;

import com.suarez.model.Supplier;
import com.suarez.service.SupplierService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class SupplierController {
    Logger logger = LoggerFactory.getLogger(SupplierController.class);

    @Autowired
    private SupplierService supplierService;

    @GetMapping("api/supplier")
    public ResponseEntity<?> listSuppliers() {
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<?> response;

        try {
            Supplier[] suppliers = supplierService.getAllSuppliers();
            response = ResponseEntity.ok().headers(headers).body(suppliers);
        } catch (Exception ex) {
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }

        return response;
    }

    @GetMapping("api/supplier/{id}")
    public ResponseEntity<?> get(@PathVariable final int id) {
        logger.info("Input supplier id >> " + id);
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<?> response;

        try {
            Supplier supplier = supplierService.getSupplierById(id);
            response = ResponseEntity.ok(supplier);
        } catch (Exception ex) {
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }

        return response;
    }

    @PostMapping("api/supplier")
    public ResponseEntity<?> add(@RequestBody Supplier supplier) {
        logger.info("Input >> " + supplier.toString());
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<?> response;

        try {
            Supplier newSupplier = supplierService.addSupplier(supplier);
            logger.info("created supplier >> " + newSupplier.toString());
            response = ResponseEntity.ok().headers(headers).body(newSupplier);
        } catch (Exception ex) {
            logger.error("Failed to create supplier: {}", ex.getMessage(), ex);
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }

        return response;
    }

    @PutMapping("api/supplier/{id}")
    public ResponseEntity<?> update(@PathVariable final int id, @RequestBody Supplier supplier) {
        logger.info("Update Input >> " + supplier.toString());
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<?> response;

        try {
            Supplier updatedSupplier = supplierService.updateSupplier(supplier);
            response = ResponseEntity.ok(updatedSupplier);
        } catch (Exception ex) {
            logger.error("Failed to update supplier: {}", ex.getMessage(), ex);
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }

        return response;
    }

    @DeleteMapping("api/supplier/{id}")
    public ResponseEntity<?> delete(@PathVariable final int id) {
        logger.info("Input >> " + id);
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<?> response;

        try {
            supplierService.deleteSupplier(id);
            response = ResponseEntity.ok(null);
        } catch (Exception ex) {
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }

        return response;
    }
}
