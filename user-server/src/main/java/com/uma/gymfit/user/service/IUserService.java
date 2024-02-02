package com.uma.gymfit.user.service;

import com.uma.gymfit.user.model.user.User;

import java.util.List;

public interface IUserService {

    /**
     * Devuelve todos los usuarios almacenados en BB DD
     *
     * @return List<U>
     */
    List<User> allUser();

    /**
     * Devuelve el usuario almacenado en BB DD
     *
     * @param idUser
     * @return User
     */
    User findUser(String idUser);


    /**
     * Crea un usuario
     *
     * @param user
     */
    void createUser(User user);


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
    void updateUser(User user);

}
