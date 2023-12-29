package com.travel.management.dao;

import com.travel.management.entities.TravelPackage;

public interface TravelPackageDao {

    TravelPackage createTravelPackage(String name, int capacity, int baseCost);

    TravelPackage getTravelPackage(String id);

    TravelPackage updateTravelPackage(String packageId, TravelPackage travelPackage);
}
