package com.travel.management.dto;

import lombok.Data;

import java.util.List;

@Data
public class TravelItineraryDto {
    private String packageName;
    private List<DestinationDto> destinationDetails;

}
