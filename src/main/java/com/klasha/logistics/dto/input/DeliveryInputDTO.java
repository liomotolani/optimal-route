package com.klasha.logistics.dto.input;


import com.klasha.logistics.model.Coordinates;
import lombok.Data;

@Data
public class DeliveryInputDTO {

    private Coordinates origin;

    private Coordinates destination;

    private String travelMode;
}
