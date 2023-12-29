package com.travel.management.dao;

import com.travel.management.entities.PackageDestinationMapping;

import java.util.List;

public interface PackageDestinationMappingDao {

    PackageDestinationMapping createPackageDestination(String packageId, String destinationId);

    List<String> getDestinationsByPackageId(String packageId);
}
