package com.klasha.logistics.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Builder
@Document
public class User {

    private String id;

    private String name;

    private String email;

    @JsonIgnore
    private String password;

    private Date createdDate;

    private Date lastLoginDate;

}
