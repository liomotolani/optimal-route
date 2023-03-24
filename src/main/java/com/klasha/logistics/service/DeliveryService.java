package com.klasha.logistics.service;

import com.klasha.logistics.dto.input.DeliveryInputDTO;
import com.klasha.logistics.dto.input.LocationInputDTO;
import com.klasha.logistics.dto.output.BasicResponseDTO;

public interface DeliveryService {


    BasicResponseDTO generateDeliveryDetails(DeliveryInputDTO dto);


}
