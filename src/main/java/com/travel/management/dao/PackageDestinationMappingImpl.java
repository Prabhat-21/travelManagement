package com.travel.management.dao;

import com.travel.management.entities.PackageDestinationMapping;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Repository
public class PackageDestinationMappingImpl implements PackageDestinationMappingDao {

    public Map<String, PackageDestinationMapping> packageDestinationMap = new HashMap<String, PackageDestinationMapping>();

    public PackageDestinationMapping createPackageDestination(String packageId, String destinationId) {
        PackageDestinationMapping packageDestinationMapping = new PackageDestinationMapping(UUID.randomUUID().toString(), packageId, destinationId);
        packageDestinationMap.put(packageDestinationMapping.getId(), packageDestinationMapping);
        return packageDestinationMapping;
    }

    public List<String> getDestinationsByPackageId(String packageId) {
        List<PackageDestinationMapping> packageDestinationMappings = packageDestinationMap.values().stream().toList();
        List<String> destinations = new ArrayList<>();
        packageDestinationMappings.forEach(packageDestinationMapping -> {
            if (Objects.equals(packageDestinationMapping.getPackageId(), packageId)) {
                destinations.add(packageDestinationMapping.getDestinationId());
            }
        });
        return destinations;
    }
}
