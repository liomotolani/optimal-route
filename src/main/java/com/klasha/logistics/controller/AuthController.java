package com.klasha.logistics.controller;

import com.klasha.logistics.dto.input.LoginInputDTO;
import com.klasha.logistics.dto.input.RegisterInputDTO;
import com.klasha.logistics.dto.output.BasicResponseDTO;
import com.klasha.logistics.dto.output.LoginResponseDTO;
import com.klasha.logistics.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/auth")
public class AuthController extends Controller {

    private final UserService userService;

    private final AuthenticationManager authenticationManager;


    @PostMapping("/register")
    public BasicResponseDTO register(@RequestBody  RegisterInputDTO dto) {
        return updateHttpStatus(userService.register(dto));
    }

    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody @Valid LoginInputDTO dto) throws Exception {
        return updateHttpStatus(userService.login(dto,authenticationManager));
    }

}
