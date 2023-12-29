package com.travel.management.controller;

import com.travel.management.dto.ActivityDetailsDto;
import com.travel.management.dto.PackagePassengerDetailsDto;
import com.travel.management.dto.TravelItineraryDto;
import com.travel.management.service.CustomerPackageService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/customer/package")
public class CustomerPackageController {
    private final CustomerPackageService customerPackageService;

    @GetMapping("/itinerary/{packageId}")
    public TravelItineraryDto getItinerary(@PathVariable("packageId") String packageId) {
        return customerPackageService.getItinerary(packageId);
    }

    @GetMapping("/passengers/{packageId}")
    public PackagePassengerDetailsDto getPassengerListByPackageId(@PathVariable("packageId") String packageId) {
        return customerPackageService.getPassengerListByPackageId(packageId);
    }

    @GetMapping("/activities")
    public ActivityDetailsDto getActivitiesWithSpaces() {
        return customerPackageService.getActivitiesWithSpaces();
    }
}
