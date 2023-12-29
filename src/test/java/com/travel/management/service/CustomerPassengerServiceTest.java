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
import com.travel.management.dto.PassengerActivityDetailsDto;
import com.travel.management.entities.Activity;
import com.travel.management.entities.Destination;
import com.travel.management.entities.MembershipType;
import com.travel.management.entities.Order;
import com.travel.management.entities.Passenger;
import com.travel.management.entities.TravelPackage;
import com.travel.management.exception.AppException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class CustomerPassengerServiceTest {
    private CustomerPassengerService customerPassengerService;

    private PassengerDao passengerDao;

    private ActivityDao activityDao;

    private TravelPackageDao travelPackageDao;

    private OrderDao orderDao;

    private OrderActivityMappingDao orderActivityMappingDao;

    private DestinationDao destinationDao;

    private PackageService packageService;

    private PackageDestinationMappingDao packageDestinationMappingDao;

    @BeforeEach
    public void setup() {
        passengerDao = new PassengerDaoImpl();
        activityDao = new ActivityDaoImpl();
        travelPackageDao = new TravelPackageDaoImpl();
        orderDao = new OrderDaoImpl();
        orderActivityMappingDao = new OrderActivityMappingDaoImpl();
        destinationDao = new DestinationDaoImpl();
        packageDestinationMappingDao = new PackageDestinationMappingImpl();
        packageService = new PackageService(travelPackageDao, packageDestinationMappingDao);
        customerPassengerService = new CustomerPassengerService(passengerDao, activityDao, travelPackageDao,
                orderDao, orderActivityMappingDao);
    }

    @Test
    public void testPlaceOrder_standardUser_successful() {
        Passenger passenger = passengerDao.createPassenger(1, "prabhat", MembershipType.STANDARD,
                100);
        Destination destination1 = destinationDao.createDestination("goa");
        Destination destination2 = destinationDao.createDestination("mumbai");
        List<String> destinations  = Stream.of(destination1,destination2).map(Destination::getId).collect(Collectors.toList());
        Activity activity1 = activityDao.createActivity("boating", "", 10, 1, destination1.getId());
        Activity activity2 = activityDao.createActivity("surfing", "", 8, 1, destination1.getId());
        Activity activity3 = activityDao.createActivity("bus ride", "", 5, 1, destination2.getId());
        TravelPackage travelPackage = packageService.registerPackage("new year travel package", 1, 5,destinations);
        List<String> activityIds = Stream.of(activity1, activity2, activity3).map(Activity::getId).collect(Collectors.toList());

        Order order = customerPassengerService.placeOrder(travelPackage.getId(), passenger.getId(), activityIds);

        Assertions.assertEquals(order.getPassengerId(), passenger.getId());
        Assertions.assertEquals(order.getPackageId(), travelPackage.getId());
        Assertions.assertNotNull(order.getId());
        List<String> activitiesByOrderId = orderActivityMappingDao.getActivitiesByOrderId(order.getId());
        assertThat(activityIds).hasSameElementsAs(activitiesByOrderId);
        Assertions.assertEquals(passenger.getBalance(), 72);
        Assertions.assertEquals(travelPackage.getEnrolled(), 1);
        Assertions.assertEquals(activity1.getRemainingCapacity(), 0);
        Assertions.assertEquals(activity2.getRemainingCapacity(), 0);
        Assertions.assertEquals(activity3.getRemainingCapacity(), 0);
    }

    @Test
    public void testPlaceOrder_standardUser_lowBalance() {
        Passenger passenger = passengerDao.createPassenger(1, "prabhat", MembershipType.STANDARD,
                10);
        Destination destination1 = destinationDao.createDestination("goa");
        Destination destination2 = destinationDao.createDestination("mumbai");
        List<String> destinations  = Stream.of(destination1,destination2).map(Destination::getId).collect(Collectors.toList());
        Activity activity1 = activityDao.createActivity("boating", "", 10, 1, destination1.getId());
        Activity activity2 = activityDao.createActivity("surfing", "", 8, 1, destination1.getId());
        Activity activity3 = activityDao.createActivity("bus ride", "", 5, 1, destination2.getId());
        TravelPackage travelPackage = packageService.registerPackage("new year travel package", 1, 5,destinations);
        List<String> activityIds = Stream.of(activity1, activity2, activity3).map(Activity::getId).collect(Collectors.toList());

        AppException exception = assertThrows(AppException.class,
                () -> customerPassengerService.placeOrder(travelPackage.getId(), passenger.getId(), activityIds));

        Assertions.assertEquals(exception.getMessage(), "Account balance is low for placing this order");
        Assertions.assertEquals(passenger.getBalance(), 10);
        Assertions.assertEquals(travelPackage.getEnrolled(), 0);
        Assertions.assertEquals(activity1.getRemainingCapacity(), 1);
        Assertions.assertEquals(activity2.getRemainingCapacity(), 1);
        Assertions.assertEquals(activity3.getRemainingCapacity(), 1);
    }

    @Test
    public void testPlaceOrder_GoldUser_successful(){
        Passenger passenger = passengerDao.createPassenger(1, "prabhat", MembershipType.GOLD,
                100);
        Destination destination1 = destinationDao.createDestination("goa");
        Destination destination2 = destinationDao.createDestination("mumbai");
        List<String> destinations  = Stream.of(destination1,destination2).map(Destination::getId).collect(Collectors.toList());
        Activity activity1 = activityDao.createActivity("boating", "", 10, 1, destination1.getId());
        Activity activity2 = activityDao.createActivity("surfing", "", 8, 1, destination1.getId());
        Activity activity3 = activityDao.createActivity("bus ride", "", 5, 1, destination2.getId());
        TravelPackage travelPackage = packageService.registerPackage("new year travel package", 1, 5,destinations);
        List<String> activityIds = Stream.of(activity1, activity2, activity3).map(Activity::getId).collect(Collectors.toList());

        Order order = customerPassengerService.placeOrder(travelPackage.getId(), passenger.getId(), activityIds);

        Assertions.assertEquals(order.getPassengerId(), passenger.getId());
        Assertions.assertEquals(order.getPackageId(), travelPackage.getId());
        Assertions.assertNotNull(order.getId());
        List<String> activitiesByOrderId = orderActivityMappingDao.getActivitiesByOrderId(order.getId());
        assertThat(activityIds).hasSameElementsAs(activitiesByOrderId);
        Assertions.assertEquals(passenger.getBalance(), 75);
        Assertions.assertEquals(travelPackage.getEnrolled(), 1);
        Assertions.assertEquals(activity1.getRemainingCapacity(), 0);
        Assertions.assertEquals(activity2.getRemainingCapacity(), 0);
        Assertions.assertEquals(activity3.getRemainingCapacity(), 0);
    }

    @Test
    public void testPlaceOrder_GoldUser_lowBalance() {
        Passenger passenger = passengerDao.createPassenger(1, "prabhat", MembershipType.GOLD,
                10);
        Destination destination1 = destinationDao.createDestination("goa");
        Destination destination2 = destinationDao.createDestination("mumbai");
        List<String> destinations  = Stream.of(destination1,destination2).map(Destination::getId).collect(Collectors.toList());
        Activity activity1 = activityDao.createActivity("boating", "", 10, 1, destination1.getId());
        Activity activity2 = activityDao.createActivity("surfing", "", 8, 1, destination1.getId());
        Activity activity3 = activityDao.createActivity("bus ride", "", 5, 1, destination2.getId());
        TravelPackage travelPackage = packageService.registerPackage("new year travel package", 1, 5,destinations);
        List<String> activityIds = Stream.of(activity1, activity2, activity3).map(Activity::getId).collect(Collectors.toList());

        AppException exception = assertThrows(AppException.class,
                () -> customerPassengerService.placeOrder(travelPackage.getId(), passenger.getId(), activityIds));

        Assertions.assertEquals(exception.getMessage(), "Account balance is low for placing this order");
        Assertions.assertEquals(passenger.getBalance(), 10);
        Assertions.assertEquals(travelPackage.getEnrolled(), 0);
        Assertions.assertEquals(activity1.getRemainingCapacity(), 1);
        Assertions.assertEquals(activity2.getRemainingCapacity(), 1);
        Assertions.assertEquals(activity3.getRemainingCapacity(), 1);
    }

    @Test
    public void testPlaceOrder_StandardUser_lowInventory() {
        Passenger passenger = passengerDao.createPassenger(1, "prabhat", MembershipType.STANDARD,
                100);
        Destination destination1 = destinationDao.createDestination("goa");
        Destination destination2 = destinationDao.createDestination("mumbai");
        List<String> destinations  = Stream.of(destination1,destination2).map(Destination::getId).collect(Collectors.toList());
        Activity activity1 = activityDao.createActivity("boating", "", 10, 1, destination1.getId());
        Activity activity2 = activityDao.createActivity("surfing", "", 8, 1, destination1.getId());
        Activity activity3 = activityDao.createActivity("bus ride", "", 5, 1, destination2.getId());
        TravelPackage travelPackage = packageService.registerPackage("new year travel package", 0, 5,destinations);
        List<String> activityIds = Stream.of(activity1, activity2, activity3).map(Activity::getId).collect(Collectors.toList());

        AppException exception = assertThrows(AppException.class,
                () -> customerPassengerService.placeOrder(travelPackage.getId(), passenger.getId(), activityIds));

        Assertions.assertEquals(exception.getMessage(), "No space left in the package : " + travelPackage.getId());
        Assertions.assertEquals(passenger.getBalance(), 100);
        Assertions.assertEquals(travelPackage.getEnrolled(), 0);
        Assertions.assertEquals(activity1.getRemainingCapacity(), 1);
        Assertions.assertEquals(activity2.getRemainingCapacity(), 1);
        Assertions.assertEquals(activity3.getRemainingCapacity(), 1);
    }

    @Test
    public void testPlaceOrder_PremiumUser_successful(){
        Passenger passenger = passengerDao.createPassenger(1, "prabhat", MembershipType.PREMIUM,
                100);
        Destination destination1 = destinationDao.createDestination("goa");
        Destination destination2 = destinationDao.createDestination("mumbai");
        List<String> destinations  = Stream.of(destination1,destination2).map(Destination::getId).collect(Collectors.toList());
        Activity activity1 = activityDao.createActivity("boating", "", 10, 1, destination1.getId());
        Activity activity2 = activityDao.createActivity("surfing", "", 8, 1, destination1.getId());
        Activity activity3 = activityDao.createActivity("bus ride", "", 5, 1, destination2.getId());
        TravelPackage travelPackage = packageService.registerPackage("new year travel package", 1, 5,destinations);
        List<String> activityIds = Stream.of(activity1, activity2, activity3).map(Activity::getId).collect(Collectors.toList());

        Order order = customerPassengerService.placeOrder(travelPackage.getId(), passenger.getId(), activityIds);

        Assertions.assertEquals(order.getPassengerId(), passenger.getId());
        Assertions.assertEquals(order.getPackageId(), travelPackage.getId());
        Assertions.assertNotNull(order.getId());
        List<String> activitiesByOrderId = orderActivityMappingDao.getActivitiesByOrderId(order.getId());
        assertThat(activityIds).hasSameElementsAs(activitiesByOrderId);
        Assertions.assertEquals(passenger.getBalance(), 95);
        Assertions.assertEquals(travelPackage.getEnrolled(), 1);
        Assertions.assertEquals(activity1.getRemainingCapacity(), 0);
        Assertions.assertEquals(activity2.getRemainingCapacity(), 0);
        Assertions.assertEquals(activity3.getRemainingCapacity(), 0);
    }

    @Test
    public void testPlaceOrder_PremiumUser_lowInventory(){
        Passenger passenger = passengerDao.createPassenger(1, "prabhat", MembershipType.PREMIUM,
                100);
        Destination destination1 = destinationDao.createDestination("goa");
        Destination destination2 = destinationDao.createDestination("mumbai");
        List<String> destinations  = Stream.of(destination1,destination2).map(Destination::getId).collect(Collectors.toList());
        Activity activity1 = activityDao.createActivity("boating", "", 10, 1, destination1.getId());
        Activity activity2 = activityDao.createActivity("surfing", "", 8, 1, destination1.getId());
        Activity activity3 = activityDao.createActivity("bus ride", "", 5, 1, destination2.getId());
        TravelPackage travelPackage = packageService.registerPackage("new year travel package", 0, 5,destinations);
        List<String> activityIds = Stream.of(activity1, activity2, activity3).map(Activity::getId).collect(Collectors.toList());

        AppException exception = assertThrows(AppException.class,
                () -> customerPassengerService.placeOrder(travelPackage.getId(), passenger.getId(), activityIds));

        Assertions.assertEquals(exception.getMessage(), "No space left in the package : " + travelPackage.getId());
        Assertions.assertEquals(passenger.getBalance(), 100);
        Assertions.assertEquals(travelPackage.getEnrolled(), 0);
        Assertions.assertEquals(activity1.getRemainingCapacity(), 1);
        Assertions.assertEquals(activity2.getRemainingCapacity(), 1);
        Assertions.assertEquals(activity3.getRemainingCapacity(), 1);
    }

    @Test
    public void testPlaceOrder_StandardUser_lowActivity_Inventory() {
        Passenger passenger = passengerDao.createPassenger(1, "prabhat", MembershipType.STANDARD,
                100);
        Destination destination1 = destinationDao.createDestination("goa");
        Destination destination2 = destinationDao.createDestination("mumbai");
        List<String> destinations  = Stream.of(destination1,destination2).map(Destination::getId).collect(Collectors.toList());
        Activity activity1 = activityDao.createActivity("boating", "", 10, 1, destination1.getId());
        Activity activity2 = activityDao.createActivity("surfing", "", 8, 1, destination1.getId());
        Activity activity3 = activityDao.createActivity("bus ride", "", 5, 0, destination2.getId());
        TravelPackage travelPackage = packageService.registerPackage("new year travel package", 3, 5,destinations);
        List<String> activityIds = Stream.of(activity1, activity2, activity3).map(Activity::getId).collect(Collectors.toList());

        AppException exception = assertThrows(AppException.class,
                () -> customerPassengerService.placeOrder(travelPackage.getId(), passenger.getId(), activityIds));

        Assertions.assertEquals(exception.getMessage(), "Capacity is full in this activity : " + activity3.getId());
        Assertions.assertEquals(passenger.getBalance(), 100);
        Assertions.assertEquals(travelPackage.getEnrolled(), 0);
        Assertions.assertEquals(activity1.getRemainingCapacity(), 1);
        Assertions.assertEquals(activity2.getRemainingCapacity(), 1);
        Assertions.assertEquals(activity3.getRemainingCapacity(), 0);
    }

    @Test
    public void getPassengerActivitiesById(){
        Passenger passenger = passengerDao.createPassenger(1, "prabhat", MembershipType.STANDARD,
                100);
        Destination destination1 = destinationDao.createDestination("goa");
        Destination destination2 = destinationDao.createDestination("mumbai");
        Activity activity1 = activityDao.createActivity("boating", "", 10, 5, destination1.getId());
        Activity activity2 = activityDao.createActivity("surfing", "", 8, 5, destination1.getId());
        Activity activity3 = activityDao.createActivity("bus ride", "", 5, 5, destination2.getId());
        TravelPackage travelPackage = travelPackageDao.createTravelPackage("new year travel package", 1, 5);
        List<String> activityIds = Stream.of(activity1, activity2, activity3).map(Activity::getId).collect(Collectors.toList());

        Order order = customerPassengerService.placeOrder(travelPackage.getId(), passenger.getId(), activityIds);

        PassengerActivityDetailsDto passengerActivityDetailsDto = customerPassengerService.getPassengerActivitiesById(passenger.getId());
        Assertions.assertEquals(passengerActivityDetailsDto.getPassengerNo(), passenger.getPassengerNo());
        Assertions.assertEquals(passengerActivityDetailsDto.getName(), passenger.getName());
        Assertions.assertEquals(passengerActivityDetailsDto.getBalance(), passenger.getBalance());
        List<String> activities = passengerActivityDetailsDto.getActivityList().stream().map(Activity::getId).collect(Collectors.toList());

        assertThat(activities).hasSameElementsAs(activityIds);
    }
}
