package com.shopperstop.user_services.controllers;

import com.shopperstop.user_services.entity.AddressDTO;
import com.shopperstop.user_services.entity.Addresses;
import com.shopperstop.user_services.service.AddressService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/address")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @PostMapping("/add-new")
    public ResponseEntity<?> createAddress(@RequestBody AddressDTO addresses) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        String username = authentication.getName();

        Addresses saved = addressService.addAddress(addresses, username);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @PutMapping("/edit-address/{label}")
    public ResponseEntity<?> editAddress(@PathVariable String label,
                                         @RequestBody AddressDTO updatedAddress) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        String username = authentication.getName();

        Addresses updated = addressService.changeAddress(label, username, updatedAddress);
        if ((updated == null)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>((updated), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{label}")
    public ResponseEntity<?> deleteAddress(@PathVariable String label) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        String username = authentication.getName();

        boolean removed = addressService.deleteAddress(label, username);
        if (removed) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/get-address/{label}")
    public ResponseEntity<?> getByLabel(@PathVariable String label){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication == null){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        String username = authentication.getName();

        Addresses required = addressService.getByLabel(label, username);
        if(required != null){
            return new ResponseEntity<Addresses>(required, HttpStatus.OK);
        }
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
