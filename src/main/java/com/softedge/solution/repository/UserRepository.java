package com.softedge.solution.repository;

import com.softedge.solution.models.UserRegistration;
import org.springframework.data.repository.CrudRepository;


public interface UserRepository extends CrudRepository<UserRegistration, Integer> {

    UserRegistration findByUsername(String username);
}
