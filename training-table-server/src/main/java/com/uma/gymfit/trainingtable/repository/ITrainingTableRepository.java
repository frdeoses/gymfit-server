package com.uma.gymfit.trainingtable.repository;

import com.uma.gymfit.trainingtable.model.training.TrainingTable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface ITrainingTableRepository extends MongoRepository<TrainingTable, String> {
    List<TrainingTable> findByUserId(String userId);

    List<TrainingTable> findByUserIdAndTypeTraining(String userId, String typeTraining);

    @Query("{ 'listTraining.id': ?0 }")
    List<TrainingTable> findByTrainingsId(String id);
}
