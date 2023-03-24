package com.klasha.logistics.serviceTest;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.klasha.logistics.config.TokenProvider;
import com.klasha.logistics.dto.enums.Status;
import com.klasha.logistics.dto.input.RegisterInputDTO;
import com.klasha.logistics.dto.output.BasicResponseDTO;
import com.klasha.logistics.model.User;
import com.klasha.logistics.repository.UserRepository;
import com.klasha.logistics.service.UserService;
import com.klasha.logistics.service.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import javax.validation.Validator;
import java.util.Optional;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
@Slf4j
public class UserServiceTest {


    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private TokenProvider tokenProvider;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private Validator validator;

    @BeforeEach
    public void setup(){
       userServiceImpl = new UserServiceImpl(userRepository,objectMapper,tokenProvider);
    }


    @Test
    public void when_user_registers_with_valid_credentials(){

        RegisterInputDTO dto = new RegisterInputDTO();
        dto.setName("Ada");
        dto.setEmail("adanmmana@gmail.com");
        dto.setPassword("admklllll");

        when(objectMapper.convertValue(dto, User.class)).thenReturn(getUser());
        BasicResponseDTO basicResponseDTO = userServiceImpl.register(dto);
        assertEquals(Status.SUCCESS,basicResponseDTO.getStatus());
        assertThat(basicResponseDTO.getData()).isNotNull();

    }



    private User getUser(){
        return User.builder()
                .id("k-1")
                .name("Omotolani")
                .email("omotolani@gmail.com")
                .password("Kininkdid")
                .build();
    }







}
