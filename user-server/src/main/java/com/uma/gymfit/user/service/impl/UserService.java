package com.uma.gymfit.user.service.impl;

import com.uma.gymfit.user.converters.ConvertUserToUserRS;
import com.uma.gymfit.user.exception.UserCreationException;
import com.uma.gymfit.user.model.dto.UserDto;
import com.uma.gymfit.user.model.dto.UserRS;
import com.uma.gymfit.user.model.user.AnthropometricData;
import com.uma.gymfit.user.model.user.PersonalData;
import com.uma.gymfit.user.model.user.RoleList;
import com.uma.gymfit.user.model.user.User;
import com.uma.gymfit.user.model.user.UserRol;
import com.uma.gymfit.user.model.user.Weight;
import com.uma.gymfit.user.repository.IUserRepository;
import com.uma.gymfit.user.service.IUserService;
import com.uma.gymfit.user.utils.Literals;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@Slf4j
public class UserService
        implements IUserService {

    private final BCryptPasswordEncoder passwordEncoder;

    private final IUserRepository repositorioUsuario;

    private final ConvertUserToUserRS convertUserToUserRS;

    @Autowired
    public UserService(BCryptPasswordEncoder passwordEncoder, IUserRepository repositorioUsuario, ConvertUserToUserRS convertUserToUserRS) {
        this.passwordEncoder = passwordEncoder;
        this.repositorioUsuario = repositorioUsuario;
        this.convertUserToUserRS = convertUserToUserRS;
    }

    /**
     * Devuelve todos los usuarios almacenados en BB DD
     *
     * @return List<User>
     */
    @Override
    public List<UserRS> allUser() {

        List<User> users = repositorioUsuario.findAll();

        return convertUserToUserRS.convert(users);
    }

    @Override
    public List<UserRS> allUserRoleUsers() {
        List<User> users = repositorioUsuario.findByUserRoles_Name(RoleList.USER.name());

        return convertUserToUserRS.convert(users);

    }

    /**
     * * Devuelve el usuario almacenado en BB DD
     *
     * @param idUser
     * @return User
     */
    @Override
    public UserRS findUser(String idUser) {
        log.info("Buscamos el usuario en el sistema con ID: {}", idUser);

        User user = repositorioUsuario.findById(idUser)
                .orElseThrow(() -> {
                    log.error("ERROR: Usuario no se encuentra en el sistema - ID: {}", idUser);
                    return new UsernameNotFoundException("Usuario no se encuentra en el sistema - ID: " + idUser);
                });

        return convertUserToUserRS.convert(user);


    }

    /**
     * Crea un usuario
     */
    @Override
    public void createUser(UserDto user) {

        try {
            //en caso de no tener problemas guardaremos en el repositorio.
            log.info("Procedemos a guardar en el sistema el siguiente usuario: {}.", user);

            validateUsernameAndEmail(user.getUsername(), user.getEmail());

            AnthropometricData anthropometricData = new AnthropometricData(user.getHeight(), user.getWeight(), 0, 0, new ArrayList<>(), new ArrayList<>());

            User newUser = new User(user.getUsername(), user.getPassword(), getPersonalData(user), anthropometricData);

            newUser.setPassword(this.passwordEncoder.encode(user.getPassword()));
            newUser.setRegistrationDate(LocalDateTime.now());

            if (newUser.getAnthropometricData().getWeight() > 0) {
                addNewWeight(newUser);
            }

            assignRole(newUser, RoleList.USER);

            repositorioUsuario.save(newUser);
            log.info("OK: Usuario guardado con éxito.");

        } catch (DataAccessException e) {
            log.error("ERROR: Error al guardar el usuario en la base de datos - {}", e.getMessage());
            throw new UserCreationException("Error al crear el usuario en la base de datos.");
        }

    }

    private void validateUsernameAndEmail(String username, String email) {

        if (repositorioUsuario.existsUserByUsername(username)) {
            log.error("ERROR: El usurario existe en el sistema con el mismo username: {}", username);
            throw new UserCreationException("El usurario existe en el sistema con el mismo username");
        }

        if (repositorioUsuario.existsUserByPersonalData_Email(email)) {
            log.error("ERROR: El usurario existe en el sistema con el mismo email: {}", email);
            throw new UserCreationException("El usurario existe en el sistema con el mismo email");
        }
    }

    /**
     * Asignamos role al usuario
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

        //comprobamos que el ID se encuentra en el repositorio
        log.info("Comprobamos en el sistema que existe el usuario en el sistema ");
        try {
            repositorioUsuario.deleteById(id);
            log.info("El usuario con ID: {} eliminada con éxito.", id);
        } catch (EmptyResultDataAccessException e) {
            log.error("El usuario con ID: {} no se encuentra en el sistema. Error: {}", id, e.getMessage());
            throw new UsernameNotFoundException("El usuario con ID: " + id + " no se encuentra en el sistema.");
        }

    }

    /**
     * Modifica un usuario
     *
     * @param user
     */
    @Override
    public UserRS updateUser(UserDto user) {

        // comprobamos que se encuentra en la BB DD
        log.info("Comprobamos en el sistema que existe el usuario.");

        User existingUser = repositorioUsuario.findById(user.getId())
                .orElseThrow(() -> new UsernameNotFoundException(Literals.USER_NOT_FOUND));

        updateFields(user, existingUser);

        // Actualiza el usuario
        repositorioUsuario.save(existingUser);
        log.info("Usuario con ID: {} actualizado con éxito.", user.getId());

        return convertUserToUserRS.convert(existingUser);

    }

    private static void updateFields(final UserDto user, final User existingUser) {

        existingUser.setPersonalData(getPersonalData(user));
        existingUser.setAnthropometricData(getAnthropometricData(user, existingUser));

        addNewWeight(existingUser);
    }

    private static @NotNull AnthropometricData getAnthropometricData(UserDto user, User existingUser) {
        return new AnthropometricData(user.getHeight(),
                user.getWeight(),
                existingUser.getAnthropometricData().getCaloriesBurned(),
                existingUser.getAnthropometricData().getHeartRate(),
                existingUser.getAnthropometricData().getListUserWeight(),
                existingUser.getAnthropometricData().getListFatPercentage());
    }

    private static @NotNull PersonalData getPersonalData(UserDto user) {
        return new PersonalData(user.getName(),
                user.getSurname(),
                user.getEmail(),
                user.getPhone(),
                user.getBirthDate());
    }

    private static void addNewWeight(final User user) {
        // Actualiza el peso si ha cambiado y añade al histórico
        log.info("Actualizando peso y añadiendo al histórico para el usuario ID: {}", user.getId());
        Weight newWeight = new Weight(LocalDateTime.now(), user.getAnthropometricData().getWeight());

        if (Objects.isNull(user.getAnthropometricData().getListUserWeight())
                || user.getAnthropometricData().getListUserWeight().isEmpty())
            user.getAnthropometricData().setListUserWeight(new ArrayList<>());

        user.getAnthropometricData().getListUserWeight().add(newWeight);
    }


}
