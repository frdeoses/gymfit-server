package com.uma.gymfit.service.impl;

import com.uma.gymfit.model.TablaEntrenamiento;
import com.uma.gymfit.model.Usuario;
import com.uma.gymfit.repository.IRepositorioTabla;
import com.uma.gymfit.repository.IRepositorioUsuario;
import com.uma.gymfit.service.IGymFitService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class GymFitService<T extends TablaEntrenamiento, U extends Usuario>
        implements IGymFitService {

    @Autowired
    private IRepositorioTabla repositorioTabla;

    @Autowired
    private IRepositorioUsuario repositorioUsuario;

    @Override
    public List allUser() {
        return repositorioUsuario.findAll();
    }

    @Override
    public List allTrainingTable() {
        return repositorioTabla.findAll();
    }

    @Override
    public void createUser(Usuario user) {

        //comprobar que no me llega un user invalido

        //en caso de no tener problemas guardaremos en el repositorio.
        repositorioUsuario.save(user);

    }

    @Override
    public void createTrainingTable(TablaEntrenamiento trainingT) {

        //comprobar que no me llega un user invalido

        //en caso de no tener problemas guardaremos en el repositorio.
        repositorioTabla.save(trainingT);


    }

    @Override
    public void deleteUser(String id) {

        //comprobamos que el id es correcto.

        //comprobamos que el id se encuentra en el reepositorio

        //una vez este todo correcto guardaremos el dato.

        repositorioUsuario.deleteById(id);

    }

    @Override
    public void deleteTrainingTable(String id) {

        //comprobamos que el id es correcto.

        //comprobamos que el id se encuentra en el reepositorio

        //una vez este todo correcto guardaremos el dato.

        repositorioTabla.deleteById(id);

    }

    @Override
    public Usuario updateUser(Usuario user) {

        //comprobamos que nos llega un usuario valido.

        // comprobamos que se encuentra en la BBDD

        //actualizamos el usuario correspondiente

        return null;
    }

    @Override
    public TablaEntrenamiento updateTrainingTable(TablaEntrenamiento trainingT) {
        return null;
    }
}
