package com.klasha.logistics.model;


import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Delivery {

    private Routes routes;

    private String deliveryCost;
}
