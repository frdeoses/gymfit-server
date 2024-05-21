package com.uma.gymfit.trainingtable.service;

import com.uma.gymfit.trainingtable.model.dtos.NewWorkedWeight;
import com.uma.gymfit.trainingtable.model.training.Training;
import com.uma.gymfit.trainingtable.model.training.TrainingType;

import java.util.List;

public interface ITrainingService {

    /**
     * Devuelve todas las tablas almacenadas en BBDD
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
     * Crea un ejercicio
     */
    void createTraining(Training training);

    /**
     * Borra un ejercicio por su id
     */
    void deleteTraining(String idTraining);

    /**
     * Modifica un ejercicio
     */
    void updateTraining(Training training);

    /**
     * Devuelve los entrenamientos del sistema segun el user pasado
     */
    List<Training> findTrainingsByUser(String userId);

    /**
     * Devuelve los entrenamientos según el tipo de entrenamiento y el
     * usuario que esté registrado en el sistema.
     */
    List<Training> findTrainingsByTrainingType(String typeTraining, String idUser);

    /**
     * Añade peso al listado de pesos en un ejercicio específico
     */
    void addNewWorkedWeights(NewWorkedWeight workedWeight);
}
