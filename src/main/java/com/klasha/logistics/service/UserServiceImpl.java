package com.klasha.logistics.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.klasha.logistics.config.TokenProvider;
import com.klasha.logistics.dto.enums.Status;
import com.klasha.logistics.dto.input.LoginInputDTO;
import com.klasha.logistics.dto.input.RegisterInputDTO;
import com.klasha.logistics.dto.output.BasicResponseDTO;
import com.klasha.logistics.dto.output.LoginResponseDTO;
import com.klasha.logistics.model.User;
import com.klasha.logistics.repository.UserRepository;
import com.klasha.logistics.util.RestUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    private final ObjectMapper mapper;

    private final TokenProvider tokenProvider;

    private BCryptPasswordEncoder bcryptEncoder = new BCryptPasswordEncoder(12);
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

        return new org.springframework.security.core.userdetails.User(username, user.getPassword(), new ArrayList<>());
    }


    @Override
    public BasicResponseDTO register(RegisterInputDTO dto) {
       try{
           Optional<User> optionalUser = userRepository.findByEmail(dto.getEmail());

           if(optionalUser.isPresent()){
               return new BasicResponseDTO(Status.CONFLICT, "User with this email already exist");
           }

           User user = mapper.convertValue(dto,User.class);
           user.setPassword(bcryptEncoder.encode(dto.getPassword()));
           user.setCreatedDate(new Date());

           userRepository.save(user);

           return new BasicResponseDTO(Status.SUCCESS, user);

       } catch (Exception ex) {
           return new BasicResponseDTO(Status.BAD_REQUEST, ex.getMessage());
       }
    }

    @Override
    public LoginResponseDTO login(LoginInputDTO dto, AuthenticationManager authenticationManager) throws Exception {

        try{
            Optional<User> user = userRepository.findByEmail(dto.getEmail());

            if(!user.isPresent()){
                return new LoginResponseDTO(Status.NOT_FOUND, "User doesn't exist");
            }

            if(!new BCryptPasswordEncoder(12).matches(dto.getPassword(), user.get().getPassword())){
                return new LoginResponseDTO(Status.BAD_REQUEST, "Incorrect Password");
            }
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            user.get().getName(),
                            dto.getPassword(),
                            new ArrayList<>()
                    )
            );

            log.info("{}",authentication);

            SecurityContextHolder.getContext().setAuthentication(authentication);

            User userObject = user.get();

            String token = tokenProvider.generateJWTToken(authentication);
            userObject.setLastLoginDate(new Date());
            userRepository.save(userObject);
            return new LoginResponseDTO(Status.SUCCESS,token,userObject);
        } catch (Exception ex){

            return new LoginResponseDTO(Status.BAD_REQUEST, ex.getMessage());
        }
    }
}
