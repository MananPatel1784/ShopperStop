package com.shopperstop.product_service.service;

import com.shopperstop.product_service.entity.Product;
import com.shopperstop.product_service.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Slf4j
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Product saveNewProduct(Product product){
        productRepository.save(product);
        return product;
    }

    public Product updateProduct(ObjectId productId, Product updatedProduct) {
        return productRepository.findById(productId)
                        .map(existingProduct -> {
                            // Update fields if new values are provided
                            if (updatedProduct.getName() != null) {
                                existingProduct.setName(updatedProduct.getName());
                            }
                            if (updatedProduct.getDescription() != null) {
                                existingProduct.setDescription(updatedProduct.getDescription());
                            }
                            if (updatedProduct.getBrand() != null) {
                                existingProduct.setBrand(updatedProduct.getBrand());
                            }
                            if (updatedProduct.getCategoryId() != null) {
                                existingProduct.setCategoryId(updatedProduct.getCategoryId());
                            }
                            if (updatedProduct.getTags() != null) {
                                existingProduct.setTags(updatedProduct.getTags());
                            }
                            if (updatedProduct.getImages() != null) {
                                existingProduct.setImages(updatedProduct.getImages());
                            }

                            existingProduct.setPrice(updatedProduct.getPrice());
                            existingProduct.setActive(updatedProduct.isActive());
                            existingProduct.setUpdatedAt(new Date());

                            return productRepository.save(existingProduct);
                        })
                        .orElseThrow(() -> new RuntimeException("Product with ID " + productId + " not found"));



    }
}
