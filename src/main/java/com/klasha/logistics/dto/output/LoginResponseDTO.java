package com.klasha.logistics.dto.output;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.klasha.logistics.dto.enums.Status;
import lombok.Data;

@Data
public class LoginResponseDTO extends StandardResponseDTO {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String token;

    private Object data;

    public LoginResponseDTO() {
    }

    public LoginResponseDTO(Status status) {
        super(status);
    }

    public LoginResponseDTO(Status status, Object data) {
        super(status);
        this.data = data;
    }

    public LoginResponseDTO(Status status, String token, Object data) {
        super(status);
        this.token = token;
        this.data = data;
    }

}
