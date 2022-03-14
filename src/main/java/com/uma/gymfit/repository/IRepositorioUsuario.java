package com.uma.gymfit.repository;

import com.uma.gymfit.model.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface IRepositorioUsuario<U extends Usuario> extends MongoRepository<Usuario,String> {

    Optional<U> findByIdUser(String id);
}
