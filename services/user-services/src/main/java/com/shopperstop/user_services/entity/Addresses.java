package com.shopperstop.user_services.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "addresses")
@Data
public class Addresses {
    private String label; // e.g., "Home", "Work"
    private String line1;
    private String line2;
    private String city;
    private String state;
    private String postalCode;
    private String country;
    private boolean defaultAddress;
}
