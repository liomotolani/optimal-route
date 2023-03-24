package com.klasha.logistics.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.klasha.logistics.config.TokenProvider;
import com.klasha.logistics.dto.enums.CountryCode;
import com.klasha.logistics.dto.enums.Status;
import com.klasha.logistics.dto.input.DeliveryInputDTO;
import com.klasha.logistics.dto.input.LocationInputDTO;
import com.klasha.logistics.dto.output.BasicResponseDTO;
import com.klasha.logistics.model.Delivery;
import com.klasha.logistics.model.Location;
import com.klasha.logistics.model.Routes;
import com.klasha.logistics.repository.LocationRepository;
import com.klasha.logistics.util.RestUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeliveryServiceImpl implements DeliveryService {

    private final LocationRepository locationRepository;

    private final RestUtils restUtils;

    @Value("${cost.per.package}")
    private double baseCostPerKilometer;


    @Override
    public BasicResponseDTO generateDeliveryDetails(DeliveryInputDTO dto) {

       try {
           List<Location> locations = locationRepository.findAll();

           if(locations.size() < 3){
               return new BasicResponseDTO(Status.BAD_REQUEST,
                       "Please add more locations before generating delivery details");
           }

           String countries = getCountries(locations);

           List<Routes> routesList = restUtils.getRoutes(dto,countries);

           if(routesList.isEmpty()) {
               return new BasicResponseDTO(Status.BAD_REQUEST,
                       "The specified location doesn't fall in the locations we deliver to, " +
                               "please enter locations within our jurisdiction. Thank you ");
           }

           double distance = routesList.get(0).getSections().get(0).getSummary().getLength();
           distance = distance / 1000;

           double deliveryCost = baseCostPerKilometer * distance;

           //Most optimal route is the first route in the list returned from the API

           Delivery delivery = Delivery.builder()
                   .routes(routesList.get(0))
                   .deliveryCost(String.format("$%.2f",deliveryCost))
                   .build();

           return new BasicResponseDTO(Status.SUCCESS, delivery);
       }catch (Exception ex) {
           log.info("{}");
           return new BasicResponseDTO(Status.INTERNAL_ERROR, ex.getMessage());
       }
    }

    private static String getCountries(List<Location> locations) {
        List<String> companyLocations = locations.stream().map(location -> location.getCountryCode()).collect(Collectors.toList());
        List<String> countryCodeList = Stream.of(CountryCode.values())
                .map(CountryCode::name)
                .collect(Collectors.toList());
        List<String> differences = countryCodeList.stream()
                .filter(element -> !companyLocations.contains(element))
                .collect(Collectors.toList());
        return String.join(",", differences);
    }

}
