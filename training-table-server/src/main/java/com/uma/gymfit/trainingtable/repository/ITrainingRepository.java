package com.uma.gymfit.trainingtable.repository;

import com.uma.gymfit.trainingtable.model.training.GymMachine;
import com.uma.gymfit.trainingtable.model.training.Training;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ITrainingRepository extends MongoRepository<Training, String> {

    List<Training> findByGymMachine(GymMachine gymMachine);

    List<Training> findByUserId(String userId);

}
