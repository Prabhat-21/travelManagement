package com.travel.management.entities;

import lombok.Data;

@Data
public class PackageDestinationMapping {
    private String id;
    private String packageId;
    private String destinationId;

    public PackageDestinationMapping(String id, String packageId, String destinationId) {
        this.id = id;
        this.packageId = packageId;
        this.destinationId = destinationId;
    }
}
