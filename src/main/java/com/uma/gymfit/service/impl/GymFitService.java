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
     *
     * @return
     */
    @Override
    public List allUser() {
        return repositorioUsuario.findAll();
    }

    /**
     * Devuelve todas las tablas almacenadas en BBDD
     *
     * @return
     */
    @Override
    public List allTrainingTable() {
        return repositorioTabla.findAll();
    }

    /**
     * Crea un usuario
     *
     * @param user
     */
    @Override
    public void createUser(Usuario user) throws Exception {

        //comprobar que no me llega un user invalido
//        Lo hacemos en el modelo
//        checkUser(user);

        //en caso de no tener problemas guardaremos en el repositorio.
        repositorioUsuario.save(user);

    }

    private void checkUser(Usuario user) throws Exception {

        if (user == null)
            throw new NullPointerException("Usuario nulo.");

        if (user.getNombre().isBlank() || user.getNombre().isEmpty())
            throw new Exception("Nombre vacio.");

        if (user.getApellidos().isBlank() || user.getApellidos().isEmpty())
            throw new Exception("Apellidos vacio.");

        if (user.getEmail().isBlank() || user.getEmail().isEmpty())
            throw new Exception("Email vacio.");

    }

    /**
     * Crea  una tabla de entrenamiento
     *
     * @param trainingT
     */
    @Override
    public void createTrainingTable(TablaEntrenamiento trainingT) throws Exception {

        //comprobar que no me llega una tabla invalido
//        Lo hacemos en el modelo
//        checkTable(trainingT);

        //en caso de no tener problemas guardaremos en el repositorio.
        repositorioTabla.save(trainingT);


    }

    private void checkTable(TablaEntrenamiento trainingT) throws Exception {
        if (trainingT == null)
            throw new NullPointerException("Tabla nula.");

        if (trainingT.getIdUsuario() == null || trainingT.getIdUsuario().isBlank() || trainingT.getIdUsuario().isEmpty())
            throw new Exception("El id del usuario es invalido.");

        if (trainingT.getTipoEntreno() == null || trainingT.getTipoEntreno().isBlank() || trainingT.getTipoEntreno().isEmpty())
            throw new Exception("Apellidos vacio.");

    }

    /**
     * Borra un usuario por su id
     *
     * @param id
     */
    @Override
    public void deleteUser(String id) throws Exception {

        //comprobamos que el id es correcto.
        if (id == null || id.isEmpty() || id.isBlank())
            throw new Exception("El id del usuario que quieres eliminar es invalido");

        //comprobamos que el id se encuentra en el reepositorio
        if (repositorioUsuario.existsById(id)) {

            //una vez este todo correcto guardaremos el dato.
            repositorioUsuario.deleteById(id);
        } else {
            throw new Exception("El usuario que quiere eliminar no se encuentra en el sistema");
        }


    }

    /**
     * Borra una tabla de entrenamiento por su id
     *
     * @param id
     */
    @Override
    public void deleteTrainingTable(String id) throws Exception {

        //comprobamos que el id es correcto.

        if (id == null || id.isEmpty() || id.isBlank())
            throw new Exception("El id de la tabla que quieres eliminar es invalido");

        //comprobamos que el id se encuentra en el reepositorio
        if (repositorioTabla.existsById(id)) {
            //una vez este todo correcto borramos el dato.
            repositorioTabla.deleteById(id);

        } else {
            throw new Exception("La tabla que quiere eliminar no se encuentra en el sistema.");
        }


    }

    /**
     * Modifica un usuario
     *
     * @param user
     */
    @Override
    public void updateUser(Usuario user) throws Exception {

        //comprobamos que nos llega un usuario valido.
//        Lo hacemos en el modelo
//        checkUser(user);

        // comprobamos que se encuentra en la BBDD
        if (repositorioUsuario.existsById(user.getId())) {

            // Borramos antrior user
//            repositorioUsuario.deleteById(user.getId());

            // insertamos nuevo
            repositorioUsuario.save(user);

        } else {
            throw new Exception("No se encuentra el usuario que quieres modificar");
        }

    }

    /**
     * Modifica una tabla de entrenamimento
     *
     * @param trainingT
     */
    @Override
    public void updateTrainingTable(TablaEntrenamiento trainingT) throws Exception {

//        comprobamos que nos llega un usuario valido.
//        Lo hacemos en el modelo
//        checkTable(trainingT);

        // comprobamos que se encuentra en la BBDD
        if (repositorioTabla.existsById(trainingT.getId())) {

            // Borramos antrior user (se supone que el save ya lo hace)
//            repositorioTabla.deleteById(trainingT.getId());

            // insertamos nuevo
            repositorioTabla.save(trainingT);

        } else {
            throw new Exception("No se encuentra la tabla que quieres modificar");
        }


    }
}
