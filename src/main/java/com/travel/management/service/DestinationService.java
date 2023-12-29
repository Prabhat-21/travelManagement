/**
 * This service is responsible for creating, fetching, and updating destination.
 */

package com.travel.management.service;

import com.travel.management.dao.DestinationDao;
import com.travel.management.entities.Destination;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class DestinationService {
    private final DestinationDao destinationDao;

    /**
     * Method creates destination by name;
     *
     * @param name
     * @return Destination
     */
    public Destination createDestination(String name) {
        return destinationDao.createDestination(name);
    }

    /**
     * @param id
     * @return Destination : fetches destination for Id.
     */
    public Destination getDestination(String id) {
        return destinationDao.getDestination(id);
    }


    /**
     * @param id
     * @param destination
     * @return updated destination
     */
    public Destination updateDestination(String id, Destination destination) {
        return destinationDao.updateDestination(id, destination);
    }
}
