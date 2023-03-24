package com.klasha.logistics.model;

import lombok.Data;

@Data
public class Place {

    private String type;

    private Coordinates location;

    private Coordinates originalLocation;

}
