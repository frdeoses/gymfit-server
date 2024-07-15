package com.uma.gymfit.trainingtable.repository;

import com.uma.gymfit.trainingtable.model.training.GymMachine;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IGymMachineRepository extends MongoRepository<GymMachine, String> {
    boolean existsByModelAndName(String model, String name);

}
