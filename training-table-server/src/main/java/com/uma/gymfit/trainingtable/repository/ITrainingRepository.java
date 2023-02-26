package com.uma.gymfit.trainingtable.repository;

import com.uma.gymfit.trainingtable.model.training.Training;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ITrainingRepository extends MongoRepository<Training,String> {

}
