package com.example.demo.transformers;

import com.example.demo.dto.request.SellerRequest;
import com.example.demo.dto.response.SellerResponse;
import com.example.demo.model.Seller;

import java.util.ArrayList;

public class SellerTransformer {

    public static Seller sellerRequestToSeller(SellerRequest sellerRequest){
        Seller seller = new Seller().builder()
                .name(sellerRequest.getName())
                .email(sellerRequest.getEmail())
                .pan(sellerRequest.getPan())
                .productList(new ArrayList<>())
                .build();

        return seller;
    }

    public static SellerResponse sellerToSellerResponse(Seller seller){
        SellerResponse sellerResponse = new SellerResponse().builder()
                .name(seller.getName())
                .email(seller.getEmail())
                .build();

        return sellerResponse;
    }
}
