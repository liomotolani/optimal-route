package com.klasha.logistics.service;

import com.klasha.logistics.dto.input.LoginInputDTO;
import com.klasha.logistics.dto.input.RegisterInputDTO;
import com.klasha.logistics.dto.output.BasicResponseDTO;
import com.klasha.logistics.dto.output.LoginResponseDTO;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.servlet.http.HttpServletRequest;

public interface UserService extends UserDetailsService {

    BasicResponseDTO register(RegisterInputDTO dto);

    LoginResponseDTO login(LoginInputDTO loginInputDTO, AuthenticationManager authenticationManager) throws Exception;
}
