package com.uma.gymfit.trainingtable.repository;

import com.uma.gymfit.trainingtable.model.training.GymMachine;
import com.uma.gymfit.trainingtable.model.training.TrainingTable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IGymMachineRepository extends MongoRepository<GymMachine, String> {

    boolean existsByModel(String model);

    GymMachine findByModel(String model);

}
