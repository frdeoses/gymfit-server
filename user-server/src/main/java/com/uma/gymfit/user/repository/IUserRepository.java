package com.uma.gymfit.user.repository;

import com.uma.gymfit.user.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IUserRepository extends MongoRepository<User,String> {

    public User findByUsername(String username);
}
