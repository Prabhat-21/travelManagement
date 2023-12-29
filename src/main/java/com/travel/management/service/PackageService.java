/**
 * This service is responsible for registering and fetching travel packages.
 */


package com.travel.management.service;

import com.travel.management.dao.PackageDestinationMappingDao;
import com.travel.management.dao.TravelPackageDao;
import com.travel.management.entities.TravelPackage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PackageService {
    private final TravelPackageDao travelPackageDao;

    private final PackageDestinationMappingDao packageDestinationMappingDao;

    /**
     * @param name
     * @param capacity
     * @param cost
     * @param destinations
     * @return TravelPackage : details of the package after registering the package in respective tables.
     */
    public TravelPackage registerPackage(String name, int capacity, int cost, List<String> destinations) {
        TravelPackage travelPackage = travelPackageDao.createTravelPackage(name, capacity, cost);
        destinations.forEach(destinationId -> packageDestinationMappingDao.createPackageDestination(travelPackage.getId(), destinationId));
        return travelPackage;
    }


    /**
     * @param id
     * @return TravelPackage: fetches package for particularId.
     */
    public TravelPackage getTravelPackage(String id) {
        return travelPackageDao.getTravelPackage(id);
    }
}
