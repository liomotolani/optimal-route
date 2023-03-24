package com.klasha.logistics.dto.input;

import lombok.Data;

import javax.validation.GroupSequence;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@GroupSequence({
        LoginInputDTO.First.class,
        LoginInputDTO.Second.class,
        LoginInputDTO.Third.class,
        LoginInputDTO.class
})
@Data
public class LoginInputDTO {

    @NotNull(message = "{user.email.notEmpty}", groups = LoginInputDTO.First.class)
    @Email(message = "{user.email.notEmpty}", groups = LoginInputDTO.Second.class)
    private String email;

    @NotNull(message = "{user.password.notEmpty}", groups = LoginInputDTO.First.class)
    @Size(min = 8, max = 150, message = "{user.password.sizeError}", groups = LoginInputDTO.Second.class)
    private String password;

    interface First {
    }

    interface Second {
    }

    interface Third {
    }
}
