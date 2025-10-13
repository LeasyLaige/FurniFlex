package com.suarez.transform;

import com.suarez.model.Product;
import com.suarez.entity.ProductData;
import org.springframework.stereotype.Service;

@Service
public class TransformProductServiceImpl implements TransformProductService {
    @Override
    public ProductData transformToEntity(Product product) {
        if (product == null) {
            return null;
        }

        ProductData productData = new ProductData();
        productData.setId(product.getId());
        productData.setName(product.getName());
        productData.setDescription(product.getDescription());
        productData.setPrice(product.getPrice());
        productData.setCreated(product.getCreated());
        productData.setLastUpdated(product.getLastUpdated());
        return productData;
    }

    @Override
    public Product transformToModel(ProductData productData) {
        if (productData == null) {
            return null;
        }

        Product product = new Product();
        product.setId(productData.getId());
        product.setName(productData.getName());
        product.setDescription(productData.getDescription());
        product.setPrice(productData.getPrice());
        product.setCreated(productData.getCreated());
        product.setLastUpdated(productData.getLastUpdated());
        return product;
    }
}
