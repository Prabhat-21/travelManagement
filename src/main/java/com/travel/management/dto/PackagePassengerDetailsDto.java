package com.travel.management.dto;

import lombok.Data;

import java.util.List;
@Data
public class PackagePassengerDetailsDto {
    private String name;
    private int capacity;
    private int enrolled;
    private List<PassengerDto> passengers;
}
