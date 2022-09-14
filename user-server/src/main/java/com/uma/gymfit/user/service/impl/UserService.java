package com.uma.gymfit.user.service.impl;

import com.uma.gymfit.user.model.User;
import com.uma.gymfit.user.repository.IUserRepository;
import com.uma.gymfit.user.service.IUserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
@Slf4j
public class UserService
        implements IUserService {


    @Autowired
    private IUserRepository repositorioUsuario;

    /**
     * Devuelve todos los usuarios almacenados en BBDD
     *
     * @return List<User>
     */
    @Override
    public List<User> allUser() {
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
    public User findUser(String idUser) throws Exception {

        log.info("Buscamos el usuario en el sistema....");
        if (repositorioUsuario.existsById(idUser)) {
            log.info("OK: User encontrado.....");
            return repositorioUsuario.findById(idUser).get();
        }

        log.error("ERROR: User no se encuentra en el sistema...");
        throw new Exception("ERROR: User no se encuentra en el sistema...");
    }


    /**
     * Crea un usuario
     *
     * @param user
     */
    @Override
    public void createUser(User user) throws Exception {

        //en caso de no tener problemas guardaremos en el repositorio.
        log.info("Procedemos a guardar en el sistema el siguiente usuario: {}.", user);
        repositorioUsuario.save(user);
        log.info("OK: User guardado con exito.");

    }

    /***
     *  Método que controla que el objeto que le llega es válido
     * @param user
     * @throws Exception
     */
    private void checkUser(User user) throws Exception {

        if (user == null)
            throw new NullPointerException("User nulo.");

        if (user.getName().isBlank() || user.getName().isEmpty())
            throw new Exception("Nombre vacio.");

        if (user.getSurname().isBlank() || user.getSurname().isEmpty())
            throw new Exception("Apellidos vacio.");

        if (user.getEmail().isBlank() || user.getEmail().isEmpty())
            throw new Exception("Email vacio.");

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
            log.info("OK: User eliminado con exito.");
        } else {
            log.error("El usuario que quiere eliminar no se encuentra en el sistema");
            throw new Exception("El usuario que quiere eliminar no se encuentra en el sistema");
        }


    }


    /**
     * Modifica un usuario
     *
     * @param user
     */
    @Override
    public void updateUser(User user) throws Exception {

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
            log.info("OK: User guardado con exito.");

        } else {
            log.error("No se encuentra el usuario que quieres modificar");
            throw new Exception("No se encuentra el usuario que quieres modificar");
        }

    }


}