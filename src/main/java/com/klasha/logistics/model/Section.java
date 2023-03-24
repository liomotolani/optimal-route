package com.klasha.logistics.model;

import lombok.Data;

import java.util.List;

@Data
public class Section {

    private String id;

    private String type;

    private List<Action> actions;

    private Points departure;

    private Points arrival;

    private Summary summary;

    private String polyline;



}
