package com.uma.gymfit.repository;

import com.uma.gymfit.model.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IRepositorioUsuario extends MongoRepository<Usuario,String> {
}
