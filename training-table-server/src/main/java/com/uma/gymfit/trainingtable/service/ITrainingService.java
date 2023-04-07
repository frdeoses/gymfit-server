package com.uma.gymfit.trainingtable.service;

import com.uma.gymfit.trainingtable.model.training.Training;
import com.uma.gymfit.trainingtable.model.training.TrainingType;
import com.uma.gymfit.trainingtable.model.user.User;

import java.util.List;

public interface ITrainingService {

    /**
     * Devuelve todas las tablas almacenadas en BBDD
     *
     * @return List<T>
     */
    public TrainingType[] trainingType();

    /**
     * Devuelve todos los ejercicios almacenados en BBDD
     *
     * @return List<T>
     */
    public List<Training> allTraining();

    /**
     * Devuelve el ejercicio almacenado en BBDD
     *
     * @return Training
     */
    public Training findTraining(String idTraining) throws Exception;

    /**
     * Crea  un ejercicio
     *
     * @param training
     */
    public void createTraining(Training training) throws Exception;

    /**
     * Borra un ejercicio por su id
     *
     * @param idTraining
     */
    public void deleteTraining(String idTraining) throws Exception;

    /**
     * Modifica un ejercicio
     *
     * @param training
     */
    public void updateTraining(Training training) throws Exception;

    /**
     * Devuelve los entrenamientos del sistema segun el user pasado
     * @param user
     * @return
     */
    List<Training> findTrainingsByUser(User user) throws Exception;

    /**
     * Devuelve los entrenamientos segun el tipo de entrenamiento y el
     * usuario que este registrado en el sistema.
     * @param typeTraining
     * @param idUser
     * @return
     */
    List<Training> findTrainingsByTrainingType(String typeTraining, String idUser) throws Exception;
}
