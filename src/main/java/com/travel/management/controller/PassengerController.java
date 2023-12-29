package com.travel.management.controller;


import com.travel.management.dto.PassengerRequestDto;
import com.travel.management.entities.Passenger;
import com.travel.management.service.PassengerService;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@AllArgsConstructor
@RequestMapping("/passengers")
public class PassengerController {

    private final PassengerService passengerService;

    @PostMapping()
    public Passenger createPassenger(@Valid @Validated @NotNull @RequestBody PassengerRequestDto passengerRequestDto) {
        return passengerService.createPassenger(passengerRequestDto.getPassengerNo(), passengerRequestDto.getName(), passengerRequestDto.getMembership(), passengerRequestDto.getBalance());
    }

    @GetMapping("/{passengerId}")
    public Passenger getPassenger(@PathVariable("passengerId") String passengerId) {
        return passengerService.getPassenger(passengerId);
    }

    @PutMapping("/{passengerId}")
    public Passenger updatePassenger(@PathVariable("passengerId") String passengerId, Passenger passenger) {
        return passengerService.updatePassenger(passengerId, passenger);
    }

}
