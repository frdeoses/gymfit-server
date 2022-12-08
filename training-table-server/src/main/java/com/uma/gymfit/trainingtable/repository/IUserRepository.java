package com.uma.gymfit.trainingtable.repository;

import com.uma.gymfit.trainingtable.model.user.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IUserRepository extends MongoRepository<User,String> {

    public User findByUsername(String username);
}
