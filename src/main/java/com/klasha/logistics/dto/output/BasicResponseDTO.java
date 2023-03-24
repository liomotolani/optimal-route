package com.klasha.logistics.dto.output;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.klasha.logistics.dto.enums.Status;
import lombok.Data;

@Data
public class BasicResponseDTO extends StandardResponseDTO {

    @JsonProperty
    private Object data;

    public BasicResponseDTO() {}

    public BasicResponseDTO(Status status) {
        super(status);
    }

    public BasicResponseDTO(Status status, Object data) {
        this.status = status;
        this.data = data;
    }

}
