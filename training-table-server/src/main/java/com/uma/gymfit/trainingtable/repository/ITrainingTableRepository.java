package com.uma.gymfit.trainingtable.repository;

import com.uma.gymfit.trainingtable.model.training.TrainingTable;
import com.uma.gymfit.trainingtable.model.user.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ITrainingTableRepository extends MongoRepository<TrainingTable, String> {

    List<TrainingTable> findByUser(User user);

}
