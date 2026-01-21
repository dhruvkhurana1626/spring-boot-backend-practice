package com.example.demo.controller;

import com.example.demo.exception.CustomerNotFound;
import com.example.demo.model.Address;
import com.example.demo.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/address")

public class AddressController {

    @Autowired
    AddressService addressService;

    @PostMapping
    public ResponseEntity addAddress (@RequestBody Address address,
                           @RequestParam ("id") int id){
        try {
            Address response = addressService.addAddress(address, id);
            return new ResponseEntity(response,HttpStatus.OK);
        } catch (CustomerNotFound e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
