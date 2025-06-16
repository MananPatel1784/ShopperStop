package com.shopperstop.user_services.entity;

import lombok.Data;

@Data
public class AddressDTO {

    private String label;
    private String line1;
    private String line2;
    private String city;
    private String state;
    private String postalCode;
    private String country;
    private boolean defaultAddress;

}
