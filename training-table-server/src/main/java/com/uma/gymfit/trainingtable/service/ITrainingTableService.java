package com.uma.gymfit.trainingtable.service;

import com.uma.gymfit.trainingtable.model.training.GymMachine;
import com.uma.gymfit.trainingtable.model.training.Training;
import com.uma.gymfit.trainingtable.model.training.TrainingTable;
import com.uma.gymfit.trainingtable.model.training.TrainingType;
import com.uma.gymfit.trainingtable.model.user.User;

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

    /**
     * Devuelve las tablas según el tipo de entrenamiento que tenga asignado
     * y el user que este logeado en la aplicacion
     *
     * @param typeTraining
     * @param idUser
     * @return
     */
    List<TrainingTable> findByTrainingType(String typeTraining, String idUser) throws Exception;

    /**
     * Devuelve las tablas segun el usuario
     *
     * @param user
     * @return
     */
    List<TrainingTable> findByUser(User user);

}
