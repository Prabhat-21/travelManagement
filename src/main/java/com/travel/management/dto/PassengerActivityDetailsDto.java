package com.travel.management.dto;

import com.travel.management.entities.Activity;
import lombok.Data;

import java.util.List;
@Data
public class PassengerActivityDetailsDto {

    private String name;
    private int passengerNo;
    private double balance;
    private List<Activity> activityList;
}
