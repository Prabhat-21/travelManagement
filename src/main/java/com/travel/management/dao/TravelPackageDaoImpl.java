package com.travel.management.dao;

import com.travel.management.entities.TravelPackage;
import com.travel.management.exception.AppException;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Repository
public class TravelPackageDaoImpl implements TravelPackageDao {

    public Map<String, TravelPackage> travelPackageMap = new HashMap<String, TravelPackage>();

    public TravelPackage createTravelPackage(String name, int capacity, int baseCost) {
        TravelPackage travelPackage = new TravelPackage(UUID.randomUUID().toString(), name, capacity, baseCost);
        travelPackageMap.put(travelPackage.getId(), travelPackage);
        return travelPackage;
    }

    public TravelPackage getTravelPackage(String id) {
        TravelPackage travelPackage = travelPackageMap.get(id);
        if (travelPackage == null) {
            throw new AppException("package does not found");
        }
        return travelPackage;
    }

    public TravelPackage updateTravelPackage(String packageId, TravelPackage travelPackage) {
        travelPackageMap.put(packageId, travelPackage);
        return travelPackage;
    }
}
