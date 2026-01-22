package com.example.demo.dto.request;

import com.example.demo.enums.Type;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class ProductRequest {
    String name;
    int price;
    Type type;
}
