package com.uma.gymfit.repository;

import com.uma.gymfit.model.TablaEntrenamiento;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IRepositorioTabla extends MongoRepository<TablaEntrenamiento,String> {
}
