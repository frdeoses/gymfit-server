package com.uma.gymfit.service.impl;

import com.uma.gymfit.model.TablaEntrenamiento;
import com.uma.gymfit.model.Usuario;
import com.uma.gymfit.repository.IRepositorioTabla;
import com.uma.gymfit.repository.IRepositorioUsuario;
import com.uma.gymfit.service.IGymFitService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
@Slf4j
public class GymFitService
        implements IGymFitService {

    @Autowired
    private IRepositorioTabla repositorioTabla;

    @Autowired
    private IRepositorioUsuario repositorioUsuario;

    /**
     * Devuelve todos los usuarios almacenados en BBDD
     *
     * @return List<Usuario>
     */
    @Override
    public List<Usuario> allUser() {
        return repositorioUsuario.findAll();
    }

    /**
     * Devuelve el usuarios almacenado en BBDD
     *
     * @param idUser
     * @return
     * @throws Exception
     */
    @Override
    public Usuario findUser(String idUser) throws Exception {

        log.info("Buscamos el usuario en el sistema....");
        if (repositorioUsuario.existsById(idUser)) {
            log.info("OK: User encontrado.....");
            return repositorioUsuario.findById(idUser).get();
        }

        log.error("ERROR: User no se encuentra en el sistema...");
        throw new Exception("ERROR: User no se encuentra en el sistema...");
    }

    /**
     * Devuelve todas las tablas almacenadas en BBDD
     *
     * @return List<TablaEntrenamiento>
     */
    @Override
    public List<TablaEntrenamiento> allTrainingTable() {
        return repositorioTabla.findAll();
    }

    /***
     * Devuelve la tablas almacenadas en BBDD
     * @param idTrainingTable
     * @return
     * @throws Exception
     */
    @Override
    public TablaEntrenamiento findTrainingTable(String idTrainingTable) throws Exception {

        log.info("Buscamos la Tabla en el sistema...");
        if (repositorioTabla.existsById(idTrainingTable)) {
            log.info("OK: TrainingTable encontrado.....");
            return repositorioTabla.findById(idTrainingTable).get();
        }

        log.error("ERROR: TrainingTable no se encuentra en el sistema...");
        throw new Exception("ERROR: TrainingTable no se encuentra en el sistema...");

    }

    /**
     * Crea un usuario
     *
     * @param user
     */
    @Override
    public void createUser(Usuario user) throws Exception {

        //en caso de no tener problemas guardaremos en el repositorio.
        log.info("Procedemos a guardar en el sistema el siguiente usuario: {}.", user);
        repositorioUsuario.save(user);
        log.info("OK: Usuario guardado con exito.");

    }

    /***
     *  Método que controla que el objeto que le llega es válido
     * @param user
     * @throws Exception
     */
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

        //en caso de no tener problemas guardaremos en el repositorio.
        log.info("Procedemos a guardar en el sistema la siguiente tabla de entrenamiento: {}.", trainingT);
        repositorioTabla.save(trainingT);
        log.info("OK: Tabla de entrenamiento guardado con exito.");


    }

    /**
     * Método que controla que el objeto que le llega es válido
     *
     * @param trainingT
     * @throws Exception
     */
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

        //comprobamos que el id se encuentra en el reepositorio
        log.info("Comprobamos en el sistema que exite el usuario en el sistema ");
        if (repositorioUsuario.existsById(id)) {

            log.info("Exite el usuario en el sistema.");
            //una vez este todo correcto guardaremos el dato.
            repositorioUsuario.deleteById(id);
            log.info("OK: Usuario eliminado con exito.");
        } else {
            log.error("El usuario que quiere eliminar no se encuentra en el sistema");
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

        //comprobamos que el id se encuentra en el reepositorio
        log.info("Comprobamos en el sistema que existe la tabla de entrenamiento. ");
        if (repositorioTabla.existsById(id)) {
            log.info("Exite la tabla de entrenamiento en el sistema.");
            //una vez este todo correcto borramos el dato.
            repositorioTabla.deleteById(id);
            log.info("OK: Tabla de entrenamiento eliminada con exito.");

        } else {
            log.error("La tabla que quiere eliminar no se encuentra en el sistema.");
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
        log.info("Comprobamos en el sistema que exite el usuario.");
        if (repositorioUsuario.existsById(user.getId())) {

            // Borramos antrior user
//            repositorioUsuario.deleteById(user.getId());
            log.info("Exite el usuario en el sistema.");
            // insertamos nuevo
            repositorioUsuario.save(user);
            log.info("OK: Usuario guardado con exito.");

        } else {
            log.error("No se encuentra el usuario que quieres modificar");
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

        // comprobamos que se encuentra en la BBDD
        log.info("Comprobamos en el sistema que existe la tabla de entrenamiento. ");
        if (repositorioTabla.existsById(trainingT.getId())) {

            // Borramos antrior user (se supone que el save ya lo hace)
//            repositorioTabla.deleteById(trainingT.getId());
            log.info("Exite la tabla de entrenamiento en el sistema.");
            // insertamos nuevo
            repositorioTabla.save(trainingT);
            log.info("OK: Tabla de entrenamiento guardado con exito.");

        } else {
            log.error("No se encuentra la tabla de entrenamiento que quieres modificar");
            throw new Exception("No se encuentra la tabla que quieres modificar");
        }

    }
}
