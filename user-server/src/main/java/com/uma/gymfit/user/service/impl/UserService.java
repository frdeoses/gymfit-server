package com.uma.gymfit.user.service.impl;

import com.uma.gymfit.user.exception.UserException;
import com.uma.gymfit.user.model.RoleList;
import com.uma.gymfit.user.model.User;
import com.uma.gymfit.user.model.UserRol;
import com.uma.gymfit.user.repository.IUserRepository;
import com.uma.gymfit.user.service.IUserService;
import com.uma.gymfit.user.utils.Literals;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Service
@Slf4j
public class UserService
        implements IUserService {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

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
    public User findUser(String idUser) throws UserException {

        log.info("Buscamos el usuario en el sistema....");
        if (repositorioUsuario.existsById(idUser)) {
            log.info("OK: User encontrado.....");
            return repositorioUsuario.findById(idUser).get();
        }

        throw new UserException(HttpStatus.BAD_REQUEST, idUser, Literals.USER_ID);

    }


    /**
     * Crea un usuario
     *
     * @param user
     */
    @Override
    public void createUser(User user) throws UserException {

        //en caso de no tener problemas guardaremos en el repositorio.
        log.info("Procedemos a guardar en el sistema el siguiente usuario: {}.", user);
        user.setId(UUID.randomUUID().toString());
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        user.setRegistrationDate(LocalDateTime.now());

        assingRole(user, RoleList.USER);

        repositorioUsuario.save(user);
        log.info("OK: User guardado con exito.");
    }

    /**
     * Asignamos role al usuario
     *
     * @param user
     * @param role
     * @return User
     */
    private User assingRole(User user, RoleList role) {

        log.info("Asignamos role: {}, al usuario: {}", role, user);
        UserRol userRol = new UserRol(UUID.randomUUID().toString(), role.name(), role);
        user.getUserRols().add(userRol);

        log.info("Role asignado correctamente!!");

        return user;
    }

    /**
     * Borra un usuario por su id
     *
     * @param id
     */
    @Override
    public void deleteUser(String id) throws UserException {

        //comprobamos que el id se encuentra en el reepositorio
        log.info("Comprobamos en el sistema que exite el usuario en el sistema ");
        if (repositorioUsuario.existsById(id)) {

            log.info("Exite el usuario en el sistema.");
            //una vez este todo correcto guardaremos el dato.
            repositorioUsuario.deleteById(id);
            log.info("OK: User eliminado con exito.");
        } else {
            throw new UserException(HttpStatus.BAD_REQUEST, id, Literals.USER_ID);
        }


    }


    /**
     * Modifica un usuario
     *
     * @param user
     */
    @Override
    public void updateUser(User user) throws UserException {

        //comprobamos que nos llega un usuario valido.
//        Lo hacemos en el modelo

        // comprobamos que se encuentra en la BBDD
        log.info("Comprobamos en el sistema que exite el usuario: {}.", user);
        if (repositorioUsuario.existsById(user.getId())) {

            // Borramos antrior user
            log.info("Exite el usuario en el sistema.");
            // insertamos nuevo
            repositorioUsuario.save(user);
            log.info("OK: User guardado con exito.");

        } else {
            throw new UserException(HttpStatus.BAD_REQUEST, user, Literals.USER);
        }

    }


}
