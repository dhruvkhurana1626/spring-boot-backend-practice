package com.example.demo.dto.response;

import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder

public class CustomerResponse {
    private String name;
    private String email;
    private Date date;
}
