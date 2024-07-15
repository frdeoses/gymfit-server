package com.uma.gymfit.trainingtable.service;

import com.uma.gymfit.trainingtable.model.training.TrainingTable;

import java.util.List;

public interface ITrainingTableService {


    /**
     * Devuelve todas las tablas almacenadas en BBDD
     *
     * @return List<T>
     */
    List<TrainingTable> allTrainingTable();


    /**
     * Devuelve la tablas almacenadas en BBDD
     *
     * @return TrainingTable
     */
    TrainingTable findTrainingTable(String idTrainingTable);


    /**
     * Crea  una tabla de entrenamiento
     *
     * @param trainingT
     */
    void createTrainingTable(TrainingTable trainingT);


    /**
     * Borra una tabla de entrenamiento por su id
     *
     * @param id
     */
    void deleteTrainingTable(String id);


    /**
     * Modifica una tabla de entrenamimento
     *
     * @param trainingT
     */
    void updateTrainingTable(TrainingTable trainingT);

    /**
     * Devuelve las tablas según el tipo de entrenamiento que tenga asignado
     * y el user que este logeado en la aplicacion
     *
     * @param typeTraining
     * @param idUser
     * @return
     */
    List<TrainingTable> findByTrainingType(String typeTraining, String idUser);

    /**
     * Devuelve las tablas segun el usuario
     *
     * @param user
     * @return
     */
    List<TrainingTable> findByUser(String userId);

}
