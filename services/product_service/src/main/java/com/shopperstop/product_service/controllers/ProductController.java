package com.shopperstop.product_service.controllers;

import com.shopperstop.product_service.client.UserClient;
import com.shopperstop.product_service.entity.Product;
import com.shopperstop.product_service.entity.UserDTO;
import com.shopperstop.product_service.service.ProductService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private UserClient userClient;

    @Value("${internal.service.token}")
    private String privateToken;


    @PostMapping("/create-product/{username}")
    public ResponseEntity<?> createProduct(@RequestBody Product product, @PathVariable String username){
        try{
            //return new ResponseEntity<String>(privateToken, HttpStatus.OK);
            UserDTO user = userClient.getUserByUsername(username, "Admin");
            if (user == null || !user.getRole().equalsIgnoreCase("ADMIN")) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("Only admins are allowed to create products.");
            }
            Product saved = productService.saveNewProduct(product);
            return new ResponseEntity<Product>(product, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<Exception>(e, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update-product/{username}/{PID}")
    public ResponseEntity<?> updateProduct(@RequestBody Product product, @PathVariable String username, @PathVariable ObjectId PID){
        try {
            UserDTO user = userClient.getUserByUsername(username, "Admin");
            if (user == null || !user.getRole().equalsIgnoreCase("ADMIN")) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("Only admins are allowed to update products.");
            }
            Product updated = productService.updateProduct(PID, product);
            return new ResponseEntity<Product>(updated, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Exception>(e, HttpStatus.BAD_REQUEST);
        }
    }
}
