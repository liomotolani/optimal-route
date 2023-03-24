package com.klasha.logistics.dto.input;

import lombok.Data;

import javax.validation.GroupSequence;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@GroupSequence({
        RegisterInputDTO.First.class,
        RegisterInputDTO.Second.class,
        RegisterInputDTO.Third.class,
        RegisterInputDTO.class
})
@Data
public class RegisterInputDTO {

    @NotNull(message = "{user.username.notEmpty}", groups = RegisterInputDTO.First.class)
    private String name;

    @NotNull(message = "{user.username.notEmpty}", groups = RegisterInputDTO.First.class)
    private String email;

    @NotNull(message = "{user.password.notEmpty}", groups = RegisterInputDTO.First.class)
    @Size(min = 8, max = 150, message = "{user.password.sizeError}", groups = RegisterInputDTO.Second.class)
    private String password;

    interface First {
    }

    interface Second {
    }

    interface Third {
    }
}
