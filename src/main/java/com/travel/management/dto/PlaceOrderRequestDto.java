package com.travel.management.dto;

import lombok.Data;

import java.util.List;

@Data
public class PlaceOrderRequestDto {
    private String packageId;
    private String passengerId;
    private List<String> activities;
}
