/**
 * The Customer package service is responsible for generating itinerary, and other things related to customer and
 * travel package.
 */

package com.travel.management.service;

import com.travel.management.dao.ActivityDao;
import com.travel.management.dao.DestinationDao;
import com.travel.management.dao.OrderDao;
import com.travel.management.dao.PackageDestinationMappingDao;
import com.travel.management.dao.PassengerDao;
import com.travel.management.dao.TravelPackageDao;
import com.travel.management.dto.ActivityDetailsDto;
import com.travel.management.dto.DestinationDto;
import com.travel.management.dto.PackagePassengerDetailsDto;
import com.travel.management.dto.PassengerDto;
import com.travel.management.dto.TravelItineraryDto;
import com.travel.management.entities.Activity;
import com.travel.management.entities.Destination;
import com.travel.management.entities.Passenger;
import com.travel.management.entities.TravelPackage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class CustomerPackageService {
    private final TravelPackageDao travelPackageDao;

    private final PackageDestinationMappingDao packageDestinationMappingDao;

    private final ActivityDao activityDao;

    private final OrderDao orderDao;

    private final PassengerDao passengerDao;

    private final DestinationDao destinationDao;


    /**
     * This method gives the itinerary for a particular packageId.
     *
     * @param packageId
     * @return package name, destination details including all the activities present at the destinations
     */
    public TravelItineraryDto getItinerary(String packageId) {
        List<String> destinations = packageDestinationMappingDao.getDestinationsByPackageId(packageId);
        TravelItineraryDto travelItineraryDto = new TravelItineraryDto();
        List<DestinationDto> destinationDtoList = new ArrayList<>();
        destinations.forEach(destinationId -> {
            Destination destination = destinationDao.getDestination(destinationId);
            DestinationDto destinationDto = new DestinationDto();
            destinationDto.setDestination(destination.getName());
            destinationDto.setActivityList(activityDao.getActivitiesByDestinationId(destinationId));
            destinationDtoList.add(destinationDto);
        });
        TravelPackage travelPackage = travelPackageDao.getTravelPackage(packageId);
        travelItineraryDto.setPackageName(travelPackage.getName());
        travelItineraryDto.setDestinationDetails(destinationDtoList);
        return travelItineraryDto;
    }

    /**
     * This method gives the passenger list for a particular packageId.
     *
     * @param packageId
     * @return passenger name, passenger number, passengers enrolled, capacity and name of the package.
     */
    public PackagePassengerDetailsDto getPassengerListByPackageId(String packageId) {
        TravelPackage travelPackage = travelPackageDao.getTravelPackage(packageId);
        List<String> passengers = orderDao.getPassengersByPackageId(packageId);
        List<PassengerDto> passengerDtoList = passengers.stream().map(passengerId -> {
            Passenger passenger = passengerDao.getPassenger(passengerId);
            PassengerDto passengerDto = new PassengerDto();
            passengerDto.setName(passenger.getName());
            passengerDto.setNumber(passenger.getPassengerNo());
            return passengerDto;
        }).collect(Collectors.toList());

        PackagePassengerDetailsDto packagePassengerDetailsDto = new PackagePassengerDetailsDto();
        packagePassengerDetailsDto.setPassengers(passengerDtoList);
        packagePassengerDetailsDto.setName(travelPackage.getName());
        packagePassengerDetailsDto.setCapacity(travelPackage.getCapacity());
        packagePassengerDetailsDto.setEnrolled(travelPackage.getEnrolled());

        return packagePassengerDetailsDto;

    }

    /**
     * This method gives all the activities that have spaces available.
     *
     * @return List<Activity> list of all activities that have available spaces for filling.
     */
    public ActivityDetailsDto getActivitiesWithSpaces() {
        List<Activity> allActivities = activityDao.getAllActivities();
        List<Activity> activitiesWithSpaces = allActivities.stream().filter(activity -> activity.getRemainingCapacity() > 0).toList();
        ActivityDetailsDto activityDetailsDto = new ActivityDetailsDto();
        activityDetailsDto.setActivityList(activitiesWithSpaces);
        return activityDetailsDto;
    }

}
