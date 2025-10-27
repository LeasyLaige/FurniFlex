package com.suarez.serviceimpl;

import com.suarez.entity.ProductData;
import com.suarez.model.Product;
import com.suarez.repository.ProductDataRepository;
import com.suarez.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Autowired
    ProductDataRepository productDataRepository;


    @Override
    public Product getProductById(int id) {
        logger.info("Getting product with id: " + id);
        Product product = null;
        Optional<ProductData> productData = productDataRepository.findById(id);

            if (productData.isPresent()) {
                product = new Product();
                product.setId(productData.get().getId());
                product.setName(productData.get().getName());
                product.setDescription(productData.get().getDescription());
                product.setType(productData.get().getType());
                product.setPrice(productData.get().getPrice());
                product.setCreated(productData.get().getCreated());
                product.setLastUpdated(productData.get().getLastUpdated());
                logger.info("Successfully found product with id: " + id);
            } else {
                logger.warn("Product with id: " + id + " not found");
            }

        return product;
    }

    @Override
    public Product[] getAllProducts() {
        List<ProductData> productDataList = new ArrayList<>();
        List<Product> productList = new ArrayList<>();

        productDataRepository.findAll().forEach(productDataList::add);
        Iterator<ProductData> iterator = productDataList.iterator();

        while (iterator.hasNext()) {
            ProductData productData = iterator.next();
            Product product = new Product();
            product.setId(productData.getId());
            product.setName(productData.getName());
            product.setDescription(productData.getDescription());
            product.setType(productData.getType());
            product.setPrice(productData.getPrice());
            product.setCreated(productData.getCreated());
            product.setLastUpdated(productData.getLastUpdated());
            productList.add(product);
        }

        Product[] productArray = new Product[productList.size()];
        for (int i = 0; i < productList.size(); i++) {
            productArray[i] = productList.get(i);
        }

        return productArray;
    }

    @Override
    public Product createProduct(Product product) {
        logger.info("Creating product with name: " + product.getName());
        ProductData productData = new ProductData();
        productData.setName(product.getName());
        productData.setDescription(product.getDescription());
        productData.setType(product.getType());
        productData.setPrice(product.getPrice());
        productData = productDataRepository.save(productData);

        logger.info("Product created with id: " + productData.getId());
        Product newProduct = new Product();
        newProduct.setId(productData.getId());
        newProduct.setName(productData.getName());
        newProduct.setDescription(productData.getDescription());
        newProduct.setType(productData.getType());
        newProduct.setPrice(productData.getPrice());

        return newProduct;
    }

    @Override
    public Product updateProduct(Product product) {
        Product updatedProduct = null;
        int id = product.getId();
        Optional<ProductData> productData = productDataRepository.findById(id);

            if (productData.isPresent()) {
                productData.get().setName(product.getName());
                productData.get().setDescription(product.getDescription());
                productData.get().setType(product.getType());
                productData.get().setPrice(product.getPrice());
                productDataRepository.save(productData.get());

                updatedProduct = new Product();
                updatedProduct.setId(productData.get().getId());
                updatedProduct.setName(productData.get().getName());
                updatedProduct.setDescription(productData.get().getDescription());
                updatedProduct.setType(productData.get().getType());
                updatedProduct.setPrice(productData.get().getPrice());

                logger.info("Successfully updated product with id: " + id);
            } else {
                logger.warn("Product with id: " + id + " not found");
            }

        return updatedProduct;
    }

    @Override
    public void deleteProduct(int id) {
        Product product = null;
        logger.info("Deleting product with id: " + id);
        Optional<ProductData> productData = productDataRepository.findById(id);

        if (productData.isPresent()) {
            productDataRepository.deleteById(id);
            logger.info("Successfully deleted product with id: " + id);
        } else {
            logger.warn("Product with id: " + id + " not found");
        }
    }
}
