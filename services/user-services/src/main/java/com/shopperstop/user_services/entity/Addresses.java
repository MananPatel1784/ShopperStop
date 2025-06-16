package com.shopperstop.user_services.entity;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Document(collection = "addresses")
@Getter
@Setter
public class Addresses {

    @Id
    private String id;

    private String label;

    private String line1;

    private String line2;

    private String city;

    private String state;

    private String postalCode;

    private String country;

    private boolean defaultAddress;

    private ObjectId userId;

    // convenient constructor
    public Addresses(AddressDTO addressDTO, ObjectId userId) {
        this.label = addressDTO.getLabel();
        this.line1 = addressDTO.getLine1();
        this.line2 = addressDTO.getLine2();
        this.city = addressDTO.getCity();
        this.state = addressDTO.getState();
        this.postalCode = addressDTO.getPostalCode();
        this.country = addressDTO.getCountry();
        this.defaultAddress = addressDTO.isDefaultAddress();
        this.userId = userId;
    }

    // default constructor
    public Addresses(){
    }
}
