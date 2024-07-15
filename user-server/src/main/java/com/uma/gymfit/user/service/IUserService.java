package com.uma.gymfit.user.service;

import com.uma.gymfit.user.model.dto.UserDto;
import com.uma.gymfit.user.model.dto.UserRS;

import java.util.List;

public interface IUserService {

    /**
     * Devuelve todos los usuarios almacenados en BB DD
     *
     * @return List<U>
     */
    List<UserRS> allUser();

    /**
     * Devuelve todos los usuarios almacenados en BB DD
     *
     * @return List<U>
     */
    List<UserRS> allUserRoleUsers();

    /**
     * Devuelve el usuario almacenado en BB DD
     *
     * @param idUser
     * @return User
     */
    UserRS findUser(String idUser);


    /**
     * Crea un usuario
     *
     * @param user
     */
    void createUser(UserDto user);


    /**
     * Borra un usuario por su id
     *
     * @param id
     */
    void deleteUser(String id);


    /**
     * Modifica un usuario
     *
     * @param user
     */
    UserRS updateUser(UserDto user);

}
