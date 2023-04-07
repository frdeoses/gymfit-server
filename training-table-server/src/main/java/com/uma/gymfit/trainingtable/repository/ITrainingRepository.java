package com.uma.gymfit.trainingtable.repository;

import com.uma.gymfit.trainingtable.model.training.GymMachine;
import com.uma.gymfit.trainingtable.model.training.Training;
import com.uma.gymfit.trainingtable.model.user.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ITrainingRepository extends MongoRepository<Training,String> {

    List<Training> findByUser(User user);

    List<Training> findByGymMachine(GymMachine gymMachine);

    boolean existsByGymMachine(GymMachine gymMachine);

}
