package com.klasha.logistics.repository;

import com.klasha.logistics.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User,String> {

    Optional<User> findByName(String name);

    Optional<User> findByEmail(String email);
}
