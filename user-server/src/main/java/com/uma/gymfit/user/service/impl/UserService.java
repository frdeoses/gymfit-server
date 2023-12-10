package com.uma.gymfit.user.service.impl;

import com.uma.gymfit.user.exception.UserCreationException;
import com.uma.gymfit.user.model.user.RoleList;
import com.uma.gymfit.user.model.user.User;
import com.uma.gymfit.user.model.user.UserRol;
import com.uma.gymfit.user.model.user.Weight;
import com.uma.gymfit.user.repository.IUserRepository;
import com.uma.gymfit.user.service.IUserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
     * Devuelve todos los usuarios almacenados en BB DD
     *
     * @return List<User>
     */
    @Override
    public List<User> allUser() {
        return repositorioUsuario.findAll();
    }

    /**
     * Devuelve el usuario almacenado en BB DD
     *
     * @param idUser
     * @return
     * @throws Exception
     */
    @Override
    public User findUser(String idUser) {
        log.info("Buscamos el usuario en el sistema con ID: {}", idUser);

        Optional<User> userOptional = repositorioUsuario.findById(idUser);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            log.info("OK: Usuario encontrado - ID: {}", user.getId());
            return user;
        } else {
            log.error("ERROR: Usuario no se encuentra en el sistema - ID: {}", idUser);
            throw new UsernameNotFoundException("Usuario no se encuentra en el sistema - ID: " + idUser);
        }
    }

    /**
     * Crea un usuario
     *
     * @param user
     */
    @Override
    public void createUser(User user) {

        try {
            //en caso de no tener problemas guardaremos en el repositorio.
            log.info("Procedemos a guardar en el sistema el siguiente usuario: {}.", user);
            user.setId(UUID.randomUUID().toString());
            user.setPassword(this.passwordEncoder.encode(user.getPassword()));
            user.setRegistrationDate(LocalDateTime.now());

            if (user.getWeight() > 0) {
                log.info("Procedemos a guardar en el sistema el peso del usuario: {}.", user.getWeight());
                Weight newWeight = new Weight(LocalDateTime.now(), user.getWeight());
                user.setListUserWeight(new ArrayList<>());
                user.getListUserWeight().add(newWeight);
            }

            assignRole(user, RoleList.USER);

            repositorioUsuario.save(user);
            log.info("OK: Usuario guardado con éxito.");

        } catch (DataAccessException e) {
            log.error("ERROR: Error al guardar el usuario en la base de datos - {}", e.getMessage());
            throw new UserCreationException("Error al crear el usuario en la base de datos.");
        }

    }

    /**
     * Asignamos role al usuario
     *
     * @param user
     * @param role
     * @return User
     */
    private void assignRole(User user, RoleList role) {

        log.info("Asignamos role: {}, al usuario: {}", role, user);
        UserRol userRol = new UserRol(UUID.randomUUID().toString(), role.name(), role);
        user.getUserRoles().add(userRol);

        log.info("Role asignado correctamente!!");

    }

    /**
     * Borra un usuario por su id
     *
     * @param id
     */
    @Override
    public void deleteUser(String id) {

        //comprobamos que el id se encuentra en el repositorio
        log.info("Comprobamos en el sistema que existe el usuario en el sistema ");
        if (repositorioUsuario.existsById(id)) {

            log.info("Existe el usuario en el sistema.");
            //una vez este correcto guardaremos el dato.
            repositorioUsuario.deleteById(id);
            log.info("OK: User eliminado con éxito.");
        } else {
            log.error("El usuario que quiere eliminar no se encuentra en el sistema - ID:{} .", id);
            throw new UsernameNotFoundException(Literals.USER_NOT_FOUND);
        }
        
    }

    /**
     * Modifica un usuario
     *
     * @param user
     */
    @Override
    public void updateUser(User user) {

        // comprobamos que se encuentra en la BBDD
        log.info("Comprobamos en el sistema que existe el usuario.");
        if (repositorioUsuario.existsById(user.getId())) {

            // Borramos anterior user
            log.info("Existe el usuario en el sistema.");
            Optional<User> oldUserSave = repositorioUsuario.findById(user.getId());

            if (oldUserSave.isEmpty()) {
                log.error(Literals.USER_NOT_FOUND);
                throw new UsernameNotFoundException(Literals.USER_NOT_FOUND);
            }

            User oldUser = oldUserSave.get();
            // insertamos nuevo
            if (oldUser.getWeight() != user.getWeight()) {
                log.info("Se ha actualizado el peso actual y procedemos a añadirlo en el histórico.");
                Weight newWeight = new Weight(LocalDateTime.now(), user.getWeight());
                user.getListUserWeight().add(newWeight);
            }
            repositorioUsuario.save(user);
            log.info("OK: User guardado con éxito.");

        } else {
            log.error(Literals.USER_NOT_FOUND);
            throw new UsernameNotFoundException(Literals.USER_NOT_FOUND);
        }

    }


}
