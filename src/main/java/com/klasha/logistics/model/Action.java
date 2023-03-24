package com.klasha.logistics.model;

import lombok.Data;

@Data
public class Action {

    private String action;

    private int duration;

    private int length;

    private int offset;

    private String direction;

    private String severity;
}
