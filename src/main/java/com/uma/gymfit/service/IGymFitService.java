package com.uma.gymfit.service;

import com.uma.gymfit.model.TablaEntrenamiento;
import com.uma.gymfit.model.Usuario;

import java.net.http.HttpResponse;
import java.util.List;

public interface IGymFitService<T extends TablaEntrenamiento, U extends Usuario> {

    /**
     * Devuelve todos los usuarios almacenados en BBDD
     * @return List<U>
     */
    public List<U> allUser();

    /**
     * Devuelve todas las tablas almacenadas en BBDD
     * @return List<T>
     */
    public List<T> allTrainingTable();

    /**
     * Crea un usuario
     * @param user
     */
    public void createUser(U user) throws Exception;

    /**
     * Crea  una tabla de entrenamiento
     * @param trainingT
     */
    public void createTrainingTable(T trainingT);

    /**
     * Borra un usuario por su id
     * @param id
     */
    public void deleteUser(String id);

    /**
     * Borra una tabla de entrenamiento por su id
     * @param id
     */
    public void deleteTrainingTable(String id);

    /**
     * Modifica un usuario
     * @param user
     * @return U
     */
    public U updateUser(U user);

    /**
     * Modifica una tabla de entrenamimento
     * @param trainingT
     * @return T
     */
    public T updateTrainingTable(T trainingT);
}
