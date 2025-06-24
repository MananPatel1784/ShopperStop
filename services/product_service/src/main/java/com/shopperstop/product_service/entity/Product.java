package com.shopperstop.product_service.entity;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document("products")
@Data
public class Product {
    @Id
    private ObjectId id;
    private String name;
    private String description;
    private String brand;
    private String categoryId;
    private List<String> tags;
    private double price;

    //private List<PriceVariant> variants;  // e.g., sizes, colors
    private List<Image> images;

    private boolean active;
    private Date createdAt;
    private Date updatedAt;
}
