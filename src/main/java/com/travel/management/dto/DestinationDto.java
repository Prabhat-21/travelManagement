package com.travel.management.dto;

import com.travel.management.entities.Activity;
import lombok.Data;

import java.util.List;

@Data
public class DestinationDto {
    private String destination;
    private List<Activity> activityList;
}
