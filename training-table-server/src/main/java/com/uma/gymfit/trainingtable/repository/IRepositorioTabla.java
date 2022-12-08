package com.uma.gymfit.trainingtable.repository;

import com.uma.gymfit.trainingtable.model.training.TrainingTable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IRepositorioTabla extends MongoRepository<TrainingTable,String> {
}
