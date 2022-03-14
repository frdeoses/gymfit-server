package com.uma.gymfit.repository;

import com.uma.gymfit.model.TablaEntrenamiento;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface IRepositorioTabla<T extends TablaEntrenamiento>
        extends MongoRepository<TablaEntrenamiento,String> {

    Optional<T> findByIdTablaEntrenamiento(String id);
}
