package com.suarez.transform;

import com.suarez.model.Product;
import com.suarez.entity.ProductData;

public interface TransformProductService {
    ProductData transformToEntity(Product product);
    Product transformToModel(ProductData productData);
}
