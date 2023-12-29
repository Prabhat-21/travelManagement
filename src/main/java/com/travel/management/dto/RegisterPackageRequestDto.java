package com.travel.management.dto;

import lombok.Data;

import java.util.List;

@Data
public class RegisterPackageRequestDto {

    private String name;
    private int capacity;
    private int cost;
    List<String> destinations;
}
