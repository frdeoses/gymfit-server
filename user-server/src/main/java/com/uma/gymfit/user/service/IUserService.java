package com.uma.gymfit.user.service;

import com.uma.gymfit.user.model.User;

import java.util.List;

public interface IUserService {

    /**
     * Devuelve todos los usuarios almacenados en BBDD
     *
     * @return List<U>
     */
    public List<User> allUser();

    /**
     * Devuelve el usuarios almacenado en BBDD
     *
     * @param idUser
     * @return User
     */
    public User findUser(String idUser) throws Exception;


    /**
     * Crea un usuario
     *
     * @param user
     */
    public void createUser(User user) throws Exception;


    /**
     * Borra un usuario por su id
     *
     * @param id
     */
    public void deleteUser(String id) throws Exception;


    /**
     * Modifica un usuario
     *
     * @param user
     */
    public void updateUser(User user) throws Exception;

}
