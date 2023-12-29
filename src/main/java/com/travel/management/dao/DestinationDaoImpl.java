package com.travel.management.dao;

import com.travel.management.entities.Destination;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Repository
public class DestinationDaoImpl implements DestinationDao {

    public Map<String, Destination> destinationMap = new HashMap<>();

    public Destination createDestination(String name) {
        Destination destination = new Destination(UUID.randomUUID().toString(), name);
        destinationMap.put(destination.getId(), destination);
        return destination;
    }

    public Destination getDestination(String id) {
        Destination destination = destinationMap.get(id);
        return destination;
    }

    public Destination updateDestination(String id, Destination destination) {
        destinationMap.put(id, destination);
        return destination;
    }
}
