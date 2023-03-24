package com.klasha.logistics.model;


import lombok.Data;

import java.util.List;

@Data
public class Routes {

    private String id;

    private List<Section> sections;

}
