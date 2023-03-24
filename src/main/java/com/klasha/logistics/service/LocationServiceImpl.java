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

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Slf4j
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;
    private final TokenProvider tokenProvider;
    private final ObjectMapper mapper;

    @Value("${cost.per.package}")
    private double baseCostPerKilometer;
    @Value("${here.route.base.url}")
    private String baseUrl;

    @Override
    public BasicResponseDTO addLocation(LocationInputDTO dto) {
        try{
            Optional<Location> optionalLocation = locationRepository.findLocationByCountryCode(dto.getCountryCode());

            if(!validCode(dto.getCountryCode())) {
                return new BasicResponseDTO(Status.BAD_REQUEST, "Invalid country code");
            }

            if(optionalLocation.isPresent()){
                return new BasicResponseDTO(Status.CONFLICT, "Location has been added");
            }

            Location location = mapper.convertValue(dto,Location.class);

            location.setCreatedDate(new Date());
            location.setAddedBy(tokenProvider.getEmail());

            locationRepository.save(location);

            return new BasicResponseDTO(Status.CREATED, location);
        } catch (Exception ex){

            return new BasicResponseDTO(Status.INTERNAL_ERROR,ex.getMessage());
        }
    }

    @Override
    public BasicResponseDTO updateLocation(String id, LocationInputDTO dto) {
        try {
            Optional<Location> locationOptional = locationRepository.findById(id);

            if(!locationOptional.isPresent()){
                return new BasicResponseDTO(Status.NOT_FOUND, "Location doesn't exist");
            }

            Location location = updateLocationObject(dto,locationOptional.get());

            locationRepository.save(location);

            return new BasicResponseDTO(Status.SUCCESS, location);

        } catch (Exception ex){
            return new BasicResponseDTO(Status.INTERNAL_ERROR, ex.getMessage());
        }
    }

    @Override
    public BasicResponseDTO fetchLocation(String id) {
        try{
            Optional<Location> location = locationRepository.findById(id);

            if(!location.isPresent()){
                return new BasicResponseDTO(Status.NOT_FOUND, "Location doesn't exist");
            }
            return new BasicResponseDTO(Status.SUCCESS, location.get());
        } catch (Exception ex){
            return new BasicResponseDTO(Status.INTERNAL_ERROR, ex.getMessage());
        }
    }

    @Override
    public BasicResponseDTO fetchLocations() {
        List<Location> locations = locationRepository.findAll();
        return new BasicResponseDTO(Status.SUCCESS, locations);
    }

    @Override
    public BasicResponseDTO deleteLocation(String id) {
        Optional<Location> location = locationRepository.findById(id);

        if(!location.isPresent()){
            return new BasicResponseDTO(Status.NOT_FOUND, "Location doesn't exist");
        }

        locationRepository.delete(location.get());
        return new BasicResponseDTO(Status.NO_CONTENT);
    }


    private Location updateLocationObject(LocationInputDTO dto, Location location){
        if(Objects.nonNull(dto.getCountryCode())) {
            location.setCountryCode(dto.getCountryCode());
        }
        return location;
    }

    private static HashSet<String> getEnums() {

        HashSet<String> values = new HashSet<String>();

        for (CountryCode c : CountryCode.values()) {
            values.add(c.name());
        }

        return values;
    }
    private boolean validCode(String code){
        return getEnums().contains(code);
    }
}
