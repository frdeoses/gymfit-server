package com.uma.gymfit.calendar.repository;

import com.uma.gymfit.calendar.model.user.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IUserRepository extends MongoRepository<User, String> {

    User findByUsername(String username);
}
