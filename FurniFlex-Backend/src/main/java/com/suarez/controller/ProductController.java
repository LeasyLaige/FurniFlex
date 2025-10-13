package com.suarez.controller;

import com.suarez.model.Product;
import com.suarez.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProductController {
    Logger logger = LoggerFactory.getLogger(ProductController.class);
    
    @Autowired
    private ProductService productService;
    
    @GetMapping("api/product")
    public ResponseEntity<?> listProducts() {
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<?> response;
        
        try {
            Product[] products = productService.getAllProducts();
            response = ResponseEntity.ok().headers(headers).body(products);
        } catch (Exception ex) {
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
        
        return response;
    }
    
    @GetMapping("api/product/{id}")
    public ResponseEntity<?> getProduct(@PathVariable final int id) {
        logger.info("Getting product with id: " + id);
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<?> response;

        try {
            Product product = productService.getProductById(id);
            response = ResponseEntity.ok(product);
        } catch (Exception ex) {
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }

        return response;
    }

    @PostMapping("api/product")
    public ResponseEntity<?> addProduct(@RequestBody Product product) {
        logger.info("Adding product: " + product);
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<?> response;

        try {
            Product newProduct = productService.createProduct(product);
            logger.info("Created product: " + newProduct);
            response = ResponseEntity.ok().headers(headers).body(newProduct);
        } catch (Exception ex) {
            logger.error("Failed to create product: {}", ex.getMessage(), ex);
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }

        return response;
    }

    @PutMapping("api/product/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable final int id, @RequestBody Product product) {
        logger.info("Updating product with id: " + id + " to " + product);
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<?> response;

        try {
            product.setId(id);
            Product updatedProduct = productService.updateProduct(product);
            logger.info("Updated product: " + updatedProduct);
            response = ResponseEntity.ok().headers(headers).body(updatedProduct);
        } catch (Exception ex) {
            logger.error("Failed to update product: {}", ex.getMessage(), ex);
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }

        return response;
    }

    @DeleteMapping("api/product/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable final int id) {
        logger.info("Deleting product with id: " + id);
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<?> response;

        try {
            productService.deleteProduct(id);
            response = ResponseEntity.ok().headers(headers).body("Product deleted successfully");
        } catch (Exception ex) {
            logger.error("Failed to delete product: {}", ex.getMessage(), ex);
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }

        return response;
    }
}
