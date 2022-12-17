package com.uma.gymfit.trainingtable.repository;

import com.uma.gymfit.trainingtable.model.training.GymMachine;
import com.uma.gymfit.trainingtable.model.training.TrainingTable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ITrainingTableRepository extends MongoRepository<TrainingTable,String> {

}
