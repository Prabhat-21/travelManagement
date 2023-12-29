package com.travel.management.dto;

import com.travel.management.entities.Activity;
import lombok.Data;

import java.util.List;

@Data
public class ActivityDetailsDto {
    private List<Activity> activityList;
}
