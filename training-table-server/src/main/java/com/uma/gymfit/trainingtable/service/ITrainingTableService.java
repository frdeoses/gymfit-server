package com.uma.gymfit.service;

import com.uma.gymfit.model.TablaEntrenamiento;
import com.uma.gymfit.model.Usuario;

import java.util.List;

public interface IGymFitService {

    /**
     * Devuelve todos los usuarios almacenados en BBDD
     * @return List<U>
     */
    public List<Usuario> allUser();

    /**
     * Devuelve el usuarios almacenado en BBDD
     * @param idUser
     * @return Usuario
     */
    public Usuario findUser(String idUser) throws Exception;

    /**
     * Devuelve todas las tablas almacenadas en BBDD
     * @return List<T>
     */
    public List<TablaEntrenamiento> allTrainingTable();

    /**
     * Devuelve la tablas almacenadas en BBDD
     * @return TablaEntrenamiento
     */
    public TablaEntrenamiento findTrainingTable(String idTrainingTable) throws Exception;

    /**
     * Crea un usuario
     * @param user
     */
    public void createUser(Usuario user) throws Exception;

    /**
     * Crea  una tabla de entrenamiento
     * @param trainingT
     */
    public void createTrainingTable(TablaEntrenamiento trainingT) throws Exception;

    /**
     * Borra un usuario por su id
     * @param id
     */
    public void deleteUser(String id) throws Exception;

    /**
     * Borra una tabla de entrenamiento por su id
     * @param id
     */
    public void deleteTrainingTable(String id) throws Exception;

    /**
     * Modifica un usuario
     * @param user
     */
    public void updateUser(Usuario user) throws Exception;

    /**
     * Modifica una tabla de entrenamimento
     * @param trainingT
     */
    public void updateTrainingTable(TablaEntrenamiento trainingT) throws Exception;
}
