package com.klasha.logistics.controller;

import com.klasha.logistics.dto.input.DeliveryInputDTO;
import com.klasha.logistics.dto.input.LocationInputDTO;
import com.klasha.logistics.dto.output.BasicResponseDTO;
import com.klasha.logistics.service.DeliveryService;
import com.klasha.logistics.service.LocationService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/delivery")
@Api(tags = "Delivery")
public class DeliveryController extends Controller {

    private final DeliveryService deliveryService;


    @PostMapping("/generate_details")
    public BasicResponseDTO generateDeliveryDetails(@RequestBody DeliveryInputDTO dto) {
        return updateHttpStatus(deliveryService.generateDeliveryDetails(dto));
    }


}
