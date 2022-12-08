package com.uma.gymfit.trainingtable.service;

import com.uma.gymfit.trainingtable.model.training.TrainingTable;

import java.util.List;

public interface ITrainingTableService {


    /**
     * Devuelve todas las tablas almacenadas en BBDD
     *
     * @return List<T>
     */
    public List<TrainingTable> allTrainingTable();

    /**
     * Devuelve la tablas almacenadas en BBDD
     *
     * @return TrainingTable
     */
    public TrainingTable findTrainingTable(String idTrainingTable) throws Exception;


    /**
     * Crea  una tabla de entrenamiento
     *
     * @param trainingT
     */
    public void createTrainingTable(TrainingTable trainingT) throws Exception;

    /**
     * Borra una tabla de entrenamiento por su id
     *
     * @param id
     */
    public void deleteTrainingTable(String id) throws Exception;


    /**
     * Modifica una tabla de entrenamimento
     *
     * @param trainingT
     */
    public void updateTrainingTable(TrainingTable trainingT) throws Exception;
}
