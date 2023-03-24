package com.klasha.logistics.service;

import com.klasha.logistics.dto.input.DeliveryInputDTO;
import com.klasha.logistics.dto.input.LocationInputDTO;
import com.klasha.logistics.dto.output.BasicResponseDTO;

public interface LocationService {

    BasicResponseDTO addLocation(LocationInputDTO dto);

    BasicResponseDTO updateLocation(String id, LocationInputDTO dto);

    BasicResponseDTO fetchLocation(String id);

    BasicResponseDTO fetchLocations();

    BasicResponseDTO deleteLocation(String id);



}
