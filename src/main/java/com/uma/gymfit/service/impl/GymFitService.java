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

    /**
     * Devuelve todos los usuarios almacenados en BBDD
     * @return
     */
    @Override
    public List allUser() {
        return repositorioUsuario.findAll();
    }

    /**
     * Devuelve todas las tablas almacenadas en BBDD
     * @return
     */
    @Override
    public List allTrainingTable() {
        return repositorioTabla.findAll();
    }

    /**
     * Crea un usuario
     * @param user
     */
    @Override
    public void createUser(Usuario user) {

        //comprobar que no me llega un user invalido

        //en caso de no tener problemas guardaremos en el repositorio.
        repositorioUsuario.save(user);

    }

    /**
     * Crea  una tabla de entrenamiento
     * @param trainingT
     */
    @Override
    public void createTrainingTable(TablaEntrenamiento trainingT) {

        //comprobar que no me llega un user invalido

        //en caso de no tener problemas guardaremos en el repositorio.
        repositorioTabla.save(trainingT);


    }

    /**
     * Borra un usuario por su id
     * @param id
     */
    @Override
    public void deleteUser(String id) {

        //comprobamos que el id es correcto.

        //comprobamos que el id se encuentra en el reepositorio

        //una vez este todo correcto guardaremos el dato.

        repositorioUsuario.deleteById(id);

    }

    /**
     * Borra una tabla de entrenamiento por su id
     * @param id
     */
    @Override
    public void deleteTrainingTable(String id) {

        //comprobamos que el id es correcto.

        //comprobamos que el id se encuentra en el reepositorio

        //una vez este todo correcto guardaremos el dato.

        repositorioTabla.deleteById(id);

    }

    /**
     * Modifica un usuario
     * @param user
     * @return
     */
    @Override
    public Usuario updateUser(Usuario user) {

        //comprobamos que nos llega un usuario valido.

        // comprobamos que se encuentra en la BBDD

        //actualizamos el usuario correspondiente

        return null;
    }

    /**
     * Modifica una tabla de entrenamimento
     * @param trainingT
     * @return
     */
    @Override
    public TablaEntrenamiento updateTrainingTable(TablaEntrenamiento trainingT) {
        return null;
    }
}
