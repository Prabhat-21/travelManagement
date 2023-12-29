package com.travel.management.dao;

import com.travel.management.entities.Destination;

public interface DestinationDao {

    Destination getDestination(String id);

    Destination createDestination(String name);

    Destination updateDestination(String id, Destination destination);
}
