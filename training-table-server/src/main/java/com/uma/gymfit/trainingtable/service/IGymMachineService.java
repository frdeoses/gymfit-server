package com.uma.gymfit.trainingtable.service;

import com.uma.gymfit.trainingtable.model.training.GymMachine;

import java.util.List;

public interface IGymMachineService {

    /**
     * Devuelve todas las máquinas almacenadas en BBDD
     *
     * @return List<T>
     */
    List<GymMachine> allGymMachine();

    /**
     * Devuelve la máquina almacenada en BBDD
     *
     * @return GymMachine
     */
    GymMachine findGymMachine(String idGymMachine);

    /**
     * Crea una máquina de entrenamiento
     *
     * @param gymMachine
     */
    void createGymMachine(GymMachine gymMachine);

    /**
     * Borra una máquina de entrenamiento por su id
     *
     * @param idGymMachine
     */
    void deleteGymMachine(String idGymMachine);

    /**
     * Modifica una máquina de entrenamiento
     *
     * @param gymMachine
     */
    void updateGymMachine(GymMachine gymMachine);


    /**
     * Delete all gym machine
     */
    void deleteAllGymMachine();
}
