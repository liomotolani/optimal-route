package com.klasha.logistics.dto.output;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.klasha.logistics.dto.enums.Status;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class StandardResponseDTO implements Serializable {


    @JsonProperty
    protected Status status;

    public StandardResponseDTO() {
    }
    public StandardResponseDTO(Status status) {
        this.status = status;
    }
}
