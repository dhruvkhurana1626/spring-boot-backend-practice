package com.example.demo.service;

import com.example.demo.model.Seller;
import com.example.demo.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SellerService {

    @Autowired
    ServiceRepository serviceRepository;

    public Seller addSeller(Seller seller) {
        Seller savedSeller = serviceRepository.save(seller);
        return savedSeller;
    }

}
