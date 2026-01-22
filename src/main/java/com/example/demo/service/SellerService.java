package com.example.demo.service;

import com.example.demo.dto.request.SellerRequest;
import com.example.demo.dto.response.SellerResponse;
import com.example.demo.model.Seller;
import com.example.demo.repository.SellerRepository;
import com.example.demo.transformers.SellerTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SellerService {

    @Autowired
    SellerRepository sellerRepository;

    public SellerResponse addSeller(SellerRequest sellerRequest) {
        Seller savedSeller = sellerRepository.save(SellerTransformer.sellerRequestToSeller(sellerRequest));
        SellerResponse sellerResponse = SellerTransformer.sellerToSellerResponse(savedSeller);
        return sellerResponse;
    }

}
