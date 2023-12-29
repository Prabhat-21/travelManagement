package com.travel.management.service;

import com.travel.management.dao.ActivityDao;
import com.travel.management.dao.ActivityDaoImpl;
import com.travel.management.dao.DestinationDao;
import com.travel.management.dao.DestinationDaoImpl;
import com.travel.management.dao.OrderActivityMappingDao;
import com.travel.management.dao.OrderActivityMappingDaoImpl;
import com.travel.management.dao.OrderDao;
import com.travel.management.dao.OrderDaoImpl;
import com.travel.management.dao.PackageDestinationMappingDao;
import com.travel.management.dao.PackageDestinationMappingImpl;
import com.travel.management.dao.PassengerDao;
import com.travel.management.dao.PassengerDaoImpl;
import com.travel.management.dao.TravelPackageDao;
import com.travel.management.dao.TravelPackageDaoImpl;
import com.travel.management.dto.DestinationDto;
import com.travel.management.dto.PackagePassengerDetailsDto;
import com.travel.management.dto.PassengerDto;
import com.travel.management.dto.TravelItineraryDto;
import com.travel.management.entities.Activity;
import com.travel.management.entities.Destination;
import com.travel.management.entities.MembershipType;
import com.travel.management.entities.Order;
import com.travel.management.entities.Passenger;
import com.travel.management.entities.TravelPackage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class CustomerPackageServiceTest {

    private CustomerPackageService customerPackageService;

    private TravelPackageDao travelPackageDao;

    private PackageDestinationMappingDao packageDestinationMappingDao;

    private ActivityDao activityDao;

    private OrderDao orderDao;

    private PassengerDao passengerDao;

    private DestinationDao destinationDao;

    private PackageService packageService;

    private CustomerPassengerService customerPassengerService;

    private OrderActivityMappingDao orderActivityMappingDao;


    @BeforeEach
    public void setup() {
        passengerDao = new PassengerDaoImpl();
        activityDao = new ActivityDaoImpl();
        travelPackageDao = new TravelPackageDaoImpl();
        orderDao = new OrderDaoImpl();
        packageDestinationMappingDao = new PackageDestinationMappingImpl();
        destinationDao = new DestinationDaoImpl();
        packageService = new PackageService(travelPackageDao, packageDestinationMappingDao);
        orderActivityMappingDao = new OrderActivityMappingDaoImpl();
        customerPassengerService = new CustomerPassengerService(passengerDao, activityDao, travelPackageDao, orderDao, orderActivityMappingDao);
        customerPackageService = new CustomerPackageService(travelPackageDao, packageDestinationMappingDao, activityDao, orderDao, passengerDao, destinationDao);
    }

    @Test
    public void testGetItinerary(){
        Passenger passenger = passengerDao.createPassenger(1, "prabhat", MembershipType.STANDARD,
                100);
        Destination destination1 = destinationDao.createDestination("goa");
        Destination destination2 = destinationDao.createDestination("mumbai");
        Activity activity1 = activityDao.createActivity("boating", "", 10, 1, destination1.getId());
        Activity activity2 = activityDao.createActivity("surfing", "", 8, 1, destination1.getId());
        Activity activity3 = activityDao.createActivity("bus ride", "", 5, 1, destination2.getId());
        List<String> destinations  = Stream.of(destination1,destination2).map(Destination::getId).collect(Collectors.toList());
        TravelPackage travelPackage = packageService.registerPackage("new year travel package", 1, 5,destinations);
        List<String> activityIds = Stream.of(activity1, activity2).map(Activity::getId).collect(Collectors.toList());
        TravelItineraryDto travelItineraryDto = customerPackageService.getItinerary(travelPackage.getId());

        Assertions.assertEquals(travelItineraryDto.getPackageName(),travelPackage.getName());
        List<DestinationDto> destinationDtoList = travelItineraryDto.getDestinationDetails();
        destinationDtoList.stream().filter(destinationDto1 -> destinationDto1.getDestination()=="goa").collect(Collectors.toList());
        assertThat(activityIds).hasSameElementsAs(destinationDtoList.get(0).getActivityList().stream().map(Activity::getId).toList());
    }

    @Test
    public void testGetPassengersByPackageId(){
        Passenger passenger1 = passengerDao.createPassenger(1, "prabhat", MembershipType.STANDARD,
                100);
        Passenger passenger2 = passengerDao.createPassenger(2, "tushar", MembershipType.GOLD,
                200);
        Passenger passenger3 = passengerDao.createPassenger(3, "shubham", MembershipType.PREMIUM,
                300);
        Destination destination1 = destinationDao.createDestination("goa");
        Destination destination2 = destinationDao.createDestination("mumbai");
        List<String> destinations  = Stream.of(destination1,destination2).map(Destination::getId).collect(Collectors.toList());
        Activity activity1 = activityDao.createActivity("boating", "", 10, 5, destination1.getId());
        Activity activity2 = activityDao.createActivity("surfing", "", 8, 5, destination1.getId());
        Activity activity3 = activityDao.createActivity("bus ride", "", 5, 5, destination2.getId());
        TravelPackage travelPackage = packageService.registerPackage("new year travel package", 3, 5,destinations);
        List<String> activityIds1 = Stream.of(activity1, activity2).map(Activity::getId).collect(Collectors.toList());
        List<String> activityIds2 = Stream.of(activity2, activity3).map(Activity::getId).collect(Collectors.toList());
        List<String> activityIds3 = Stream.of(activity1, activity3).map(Activity::getId).collect(Collectors.toList());

        Order order1 = customerPassengerService.placeOrder(travelPackage.getId(), passenger1.getId(), activityIds1);
        Order order2 = customerPassengerService.placeOrder(travelPackage.getId(), passenger2.getId(), activityIds2);
        Order order3 = customerPassengerService.placeOrder(travelPackage.getId(), passenger3.getId(), activityIds3);

        PackagePassengerDetailsDto packagePassengerDetailsDto = customerPackageService.getPassengerListByPackageId(travelPackage.getId());

        Assertions.assertEquals(packagePassengerDetailsDto.getCapacity(), travelPackage.getCapacity());
        Assertions.assertEquals(packagePassengerDetailsDto.getName(), travelPackage.getName());
        Assertions.assertEquals(packagePassengerDetailsDto.getEnrolled(), travelPackage.getEnrolled());
        List<Integer> passengers = packagePassengerDetailsDto.getPassengers().stream().map(PassengerDto::getNumber).collect(Collectors.toList());

        List<Integer> actualPassengerList = Stream.of(passenger1, passenger2, passenger3).map(Passenger::getPassengerNo).collect(Collectors.toList());

        assertThat(actualPassengerList).hasSameElementsAs(passengers);

    }
}
