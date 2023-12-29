package com.travel.management.controller;

import com.travel.management.dto.PassengerActivityDetailsDto;
import com.travel.management.dto.PlaceOrderRequestDto;
import com.travel.management.entities.Order;
import com.travel.management.service.CustomerPassengerService;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/customer/passenger")
public class CustomerPassengerController {
    private final CustomerPassengerService customerPassengerService;

    @PostMapping("/order")
    public Order placeOrder(@Validated @Valid @NotNull @RequestBody PlaceOrderRequestDto placeOrderRequestDto) {
        return customerPassengerService.placeOrder(placeOrderRequestDto.getPackageId(), placeOrderRequestDto.getPassengerId(), placeOrderRequestDto.getActivities());
    }

    @GetMapping("/activities/{passengerId}")
    public PassengerActivityDetailsDto getPassengerActivitiesById(@PathVariable("passengerId") String passengerId) {
        return customerPassengerService.getPassengerActivitiesById(passengerId);
    }
}
