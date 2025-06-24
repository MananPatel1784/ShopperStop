package com.shopperstop.product_service.entity;

import lombok.Data;

@Data
public class Image {
    private String url;
    private String altText;
    private boolean isPrimary;
}
