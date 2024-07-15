package com.uma.gymfit.user.repository;

import com.uma.gymfit.user.model.user.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface IUserRepository extends MongoRepository<User, String> {

    @Query("{ 'userRoles.nameRole': ?0 }")
    List<User> findByUserRoles_Name(String roleName);

    User findByUsername(String username);

    boolean existsUserByUsername(String username);

    boolean existsUserByPersonalData_Email(String email);
}
