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
    TrainingType[] trainingType();

    /**
     * Devuelve todos los ejercicios almacenados en BBDD
     *
     * @return List<T>
     */
    List<Training> allTraining();

    /**
     * Devuelve el ejercicio almacenado en BBDD
     *
     * @return Training
     */
    Training findTraining(String idTraining);

    /**
     * Crea  un ejercicio
     *
     * @param training
     */
    void createTraining(Training training);

    /**
     * Borra un ejercicio por su id
     *
     * @param idTraining
     */
    void deleteTraining(String idTraining);

    /**
     * Modifica un ejercicio
     *
     * @param training
     */
    void updateTraining(Training training);

    /**
     * Devuelve los entrenamientos del sistema segun el user pasado
     *
     * @param user
     * @return
     */
    List<Training> findTrainingsByUser(User user);

    /**
     * Devuelve los entrenamientos segun el tipo de entrenamiento y el
     * usuario que este registrado en el sistema.
     *
     * @param typeTraining
     * @param idUser
     * @return
     */
    List<Training> findTrainingsByTrainingType(String typeTraining, String idUser);
}
