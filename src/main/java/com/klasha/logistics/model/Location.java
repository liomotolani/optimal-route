package com.klasha.logistics.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document
public class Location {

    private String id;

    private String countryCode;

    private Date createdDate;

    private String addedBy;

    private Date lastUpdatedTime;

}
