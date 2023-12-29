package com.travel.management.dto;

import com.travel.management.entities.MembershipType;
import lombok.Data;


@Data
public class PassengerRequestDto {

    private int passengerNo;
    private String name;
    private MembershipType membership;
    private double balance;
}
