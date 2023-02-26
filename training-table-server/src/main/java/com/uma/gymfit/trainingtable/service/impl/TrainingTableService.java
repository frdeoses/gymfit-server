package com.uma.gymfit.trainingtable.service.impl;

import com.uma.gymfit.trainingtable.model.training.GymMachine;
import com.uma.gymfit.trainingtable.model.training.Training;
import com.uma.gymfit.trainingtable.model.training.TrainingTable;
import com.uma.gymfit.trainingtable.model.training.TrainingType;
import com.uma.gymfit.trainingtable.repository.IGymMachineRepository;
import com.uma.gymfit.trainingtable.repository.ITrainingRepository;
import com.uma.gymfit.trainingtable.repository.ITrainingTableRepository;
import com.uma.gymfit.trainingtable.service.ITrainingTableService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Service
@Slf4j
public class TrainingTableService
        implements ITrainingTableService {

    @Autowired
    private ITrainingTableRepository trainingTableRepository;

    @Autowired
    private IGymMachineRepository gymMachineRepository;

    @Autowired
    private ITrainingRepository trainingRepository;


    /**
     * Devuelve todas las tablas almacenadas en BBDD
     *
     * @return List<TrainingTable>
     */
    @Override
    public List<TrainingTable> allTrainingTable() {
        return trainingTableRepository.findAll();
    }

    /**
     * Devuelve todas las tablas almacenadas en BBDD
     *
     * @return List<TrainingTable>
     */
    @Override
    public TrainingType[] trainingType() {
        return TrainingType.values();
    }

    /**
     * Devuelve todas las maquinas almacenadas en BBDD
     *
     * @return List<TrainingTable>
     */
    @Override
    public List<GymMachine> allGymMachine() {
        return gymMachineRepository.findAll();
    }

    /**
     * Devuelve todas los ejercicios almacenadas en BBDD
     *
     * @return List<TrainingTable>
     */
    @Override
    public List<Training> allTraining() {
        return trainingRepository.findAll();
    }

    /***
     * Devuelve la tablas almacenadas en BBDD
     * @param idTrainingTable
     * @return
     * @throws Exception
     */
    @Override
    public TrainingTable findTrainingTable(String idTrainingTable) throws Exception {

        log.info("Buscamos la Tabla en el sistema...");
        if (trainingTableRepository.existsById(idTrainingTable)) {
            log.info("OK: TrainingTable encontrado.....");
            return trainingTableRepository.findById(idTrainingTable).get();
        }

        log.error("ERROR: TrainingTable no se encuentra en el sistema...");
        throw new Exception("ERROR: TrainingTable no se encuentra en el sistema...");

    }

    /***
     * Devuelve la tablas almacenadas en BBDD
     * @param idGymMachine
     * @return
     * @throws Exception
     */
    @Override
    public GymMachine findGymMachine(String idGymMachine) throws Exception {

        log.info("Buscamos la Maquina en el sistema...");
        if (gymMachineRepository.existsById(idGymMachine)) {
            log.info("OK: Maquina encontrada.....");
            return gymMachineRepository.findById(idGymMachine).get();
        }

        log.error("ERROR: La maquina no se encuentra en el sistema...");
        throw new Exception("ERROR: La maquina no se encuentra en el sistema...");

    }

    /***
     * Devuelve la tablas almacenadas en BBDD
     * @param idTraining
     * @return
     * @throws Exception
     */
    @Override
    public Training findTraining(String idTraining) throws Exception {

        log.info("Buscamos el ejercicio en el sistema...");
        if (trainingRepository.existsById(idTraining)) {
            log.info("OK: Ejercicio encontrada.....");
            return trainingRepository.findById(idTraining).get();
        }

        log.error("ERROR: El ejercicio no se encuentra en el sistema...");
        throw new Exception("ERROR: El ejercicio no se encuentra en el sistema...");

    }


    /**
     * Crea  una tabla de entrenamiento
     *
     * @param trainingT
     */
    @Override
    public void createTrainingTable(TrainingTable trainingT) throws Exception {

        //en caso de no tener problemas guardaremos en el repositorio.
        log.info("Procedemos a guardar en el sistema la siguiente tabla de entrenamiento: {}.", trainingT);
        trainingT.setId(UUID.randomUUID().toString());
        trainingTableRepository.save(trainingT);
        log.info("OK: Tabla de entrenamiento guardado con exito.");

    }

    /**
     * Crea  una maquina
     *
     * @param gymMachine
     */
    @Override
    public void createGymMachine(GymMachine gymMachine) throws Exception {

        //en caso de no tener problemas guardaremos en el repositorio.
        log.info("Procedemos a guardar en el sistema la siguiente maquina: {}.", gymMachine);
        gymMachine.setId(UUID.randomUUID().toString());
        gymMachineRepository.save(gymMachine);
        log.info("OK: Tabla de entrenamiento guardado con exito.");

    }

    /**
     * Crea  un Ejercicio
     *
     * @param training
     */
    @Override
    public void createTraining(Training training) throws Exception {

        //en caso de no tener problemas guardaremos en el repositorio.
        log.info("Procedemos a guardar en el sistema el siguiente ejercicio: {}.", training);
        training.setId(UUID.randomUUID().toString());
        training.setCreationDate(LocalDateTime.now());
        training.setLastUpdateDate(LocalDateTime.now());
        trainingRepository.save(training);
        log.info("OK: Ejercicio guardado con exito.");

    }


    /**
     * Borra una tabla de entrenamiento por su id
     *
     * @param id
     */
    @Override
    public void deleteTrainingTable(String id) throws Exception {

        //comprobamos que el id se encuentra en el reepositorio
        log.info("Comprobamos en el sistema que existe la tabla de entrenamiento. ");
        if (trainingTableRepository.existsById(id)) {
            log.info("Exite la tabla de entrenamiento en el sistema.");
            //una vez este todo correcto borramos el dato.
            trainingTableRepository.deleteById(id);
            log.info("OK: Tabla de entrenamiento eliminada con exito.");

        } else {
            log.error("La tabla que quiere eliminar no se encuentra en el sistema.");
            throw new Exception("La tabla que quiere eliminar no se encuentra en el sistema.");
        }

    }

    /**
     * Borra una maquina de entrenamiento por su id
     *
     * @param idGymMachine
     */
    @Override
    public void deleteGymMachine(String idGymMachine) throws Exception {

        //comprobamos que el id se encuentra en el reepositorio
        log.info("Comprobamos en el sistema que existe la maquina de entrenamiento. ");
        if (gymMachineRepository.existsById(idGymMachine)) {
            log.info("Exite la maquina de entrenamiento en el sistema.");
            //una vez este todo correcto borramos el dato.
            gymMachineRepository.deleteById(idGymMachine);
            log.info("OK: Maquina de entrenamiento eliminada con exito.");

        } else {
            log.error("La maquina que quiere eliminar no se encuentra en el sistema.");
            throw new Exception("La maquina que quiere eliminar no se encuentra en el sistema.");
        }

    }

    /**
     * Borra un ejercicio por su id
     *
     * @param idTraining
     */
    @Override
    public void deleteTraining(String idTraining) throws Exception {

        //comprobamos que el id se encuentra en el reepositorio
        log.info("Comprobamos en el sistema que existe el ejercicio. ");
        if (trainingRepository.existsById(idTraining)) {
            log.info("Exite el ejercicio en el sistema.");
            //una vez este todo correcto borramos el dato.
            trainingRepository.deleteById(idTraining);
            log.info("OK: Ejercicio eliminado con exito.");

        } else {
            log.error("El ejercicio que quiere eliminar no se encuentra en el sistema.");
            throw new Exception("El ejercicio que quiere eliminar no se encuentra en el sistema.");
        }

    }

    /**
     * Modifica una tabla de entrenamimento
     *
     * @param trainingT
     */
    @Override
    public void updateTrainingTable(TrainingTable trainingT) throws Exception {

        // comprobamos que se encuentra en la BBDD
        log.info("Comprobamos en el sistema que existe la tabla de entrenamiento. ");
        if (trainingTableRepository.existsById(trainingT.getId())) {

            log.info("Exite la tabla de entrenamiento en el sistema.");
            // insertamos nuevo
            trainingTableRepository.save(trainingT);
            log.info("OK: Tabla de entrenamiento actualizado con exito.");

        } else {
            log.error("No se encuentra la tabla de entrenamiento que quieres modificar");
            throw new Exception("No se encuentra la tabla que quieres modificar");
        }

    }/**
     * Modifica una máquina de entrenamimento
     *
     * @param gymMachine
     */
    @Override
    public void updateGymMachine(GymMachine gymMachine) throws Exception {

        // comprobamos que se encuentra en la BBDD
        log.info("Comprobamos en el sistema que existe la máquina  de entrenamiento. ");
        if (gymMachineRepository.existsById(gymMachine.getId())) {

            log.info("Exite la mñaquina de entrenamiento en el sistema.");
            // insertamos nuevo
            gymMachineRepository.save(gymMachine);
            log.info("OK: Máquina de entrenamiento actualizado con exito.");

        } else {
            log.error("No se encuentra la mñaquina de entrenamiento que quieres modificar");
            throw new Exception("No se encuentra la máquina de entrenamiento que quieres modificar");
        }

    }

    /**
     * Modifica un ejercicio
     *
     * @param training
     */
    @Override
    public void updateTraining(Training training) throws Exception {

        // comprobamos que se encuentra en la BBDD
        log.info("Comprobamos en el sistema que existe el ejercicio. ");
        if (trainingRepository.existsById(training.getId())) {

            training.setLastUpdateDate(LocalDateTime.now());

            log.info("Exite el ejercicio en el sistema.");
            // insertamos nuevo
            trainingRepository.save(training);
            log.info("OK: Ejercicio actualizado con exito.");

        } else {
            log.error("No se encuentra el ejercicio que quieres modificar");
            throw new Exception("No se encuentra el ejercicio que quieres modificar");
        }

    }
}
