package com.shopperstop.product_service.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("categories")
@Data
public class Category {
    @Id
    private String id;
    private String name;
    private String parentId; // for subcategories
}
