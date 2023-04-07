package com.uma.gymfit.trainingtable.service;

import com.uma.gymfit.trainingtable.model.training.GymMachine;

import java.util.List;

public interface IGymMachineService {

    /**
     * Devuelve todas las maquinas almacenadas en BBDD
     *
     * @return List<T>
     */
    public List<GymMachine> allGymMachine();

    /**
     * Devuelve la maquina almacenadas en BBDD
     *
     * @return GymMachine
     */
    public GymMachine findGymMachine(String idGymMachine) throws Exception;

    /**
     * Crea  una maquina de entrenamiento
     *
     * @param gymMachine
     */
    public void createGymMachine(GymMachine gymMachine) throws Exception;

    /**
     * Borra una maquina de entrenamiento por su id
     *
     * @param idGymMachine
     */
    public void deleteGymMachine(String idGymMachine) throws Exception;

    /**
     * Modifica una maquina de entrenamiento
     *
     * @param gymMachine
     */
    public void updateGymMachine(GymMachine gymMachine) throws Exception;
}
