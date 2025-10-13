package com.suarez.service;

import com.suarez.model.Product;

public interface ProductService {
    Product getProductById(int id);
    Product[] getAllProducts();
    Product createProduct(Product product);
    Product updateProduct(Product product);
    void deleteProduct(int id);
}
