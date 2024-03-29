package com.uma.gymfit.trainingtable.service.impl;

import com.uma.gymfit.trainingtable.exception.ValidationException;
import com.uma.gymfit.trainingtable.exception.machine.GymMachineCreateException;
import com.uma.gymfit.trainingtable.exception.machine.GymMachineNotFoundException;
import com.uma.gymfit.trainingtable.model.training.GymMachine;
import com.uma.gymfit.trainingtable.model.training.Training;
import com.uma.gymfit.trainingtable.repository.IGymMachineRepository;
import com.uma.gymfit.trainingtable.repository.ITrainingRepository;
import com.uma.gymfit.trainingtable.service.IGymMachineService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@AllArgsConstructor
@Service
@Slf4j
public class GymMachineService implements IGymMachineService {

    private final IGymMachineRepository gymMachineRepository;

    private final ITrainingRepository trainingRepository;

    /**
     * Devuelve todas las máquinas almacenadas en BBDD
     *
     * @return List<TrainingTable>
     */
    @Override
    public List<GymMachine> allGymMachine() {
        return gymMachineRepository.findAll();
    }

    /***
     * Devuelve las tablas almacenadas en BBDD
     * @param idGymMachine
     * @return
     * @throws Exception
     */
    @Override
    public GymMachine findGymMachine(String idGymMachine) {

        log.info("Buscamos la maquina en el sistema con ID: {}", idGymMachine);

        return gymMachineRepository.findById(idGymMachine)
                .orElseThrow(() -> {
                    log.error("ERROR: La maquina no se encuentra en el sistema - ID: {}", idGymMachine);
                    return new GymMachineNotFoundException("Maquina no se encuentra en el sistema - ID: " + idGymMachine);
                });

    }

    /**
     * Crea  una maquina
     *
     * @param gymMachine
     */
    @Override
    public void createGymMachine(GymMachine gymMachine) {

        validateGymMachine(gymMachine);

        try {

            gymMachine.setCreationDate(LocalDateTime.now());
            gymMachine.setLastUpdateDate(LocalDateTime.now());
            gymMachineRepository.save(gymMachine);

            log.info("OK: Máquina de entrenamiento guardado con éxito.");
        } catch (DataAccessException e) {
            log.error("ERROR: Error al guardar la maquina en la base de datos - {}", e.getMessage());
            throw new GymMachineCreateException("Error al crear la máquina en la base de datos.");
        }


    }

    private void validateGymMachine(GymMachine gymMachine) {
        
        if (Objects.isNull(gymMachine)) {
            throw new IllegalArgumentException("La máquina no puede ser null.");
        }

        log.info("Procedemos a guardar en el sistema la siguiente maquina: {}.", gymMachine);

        if (gymMachineRepository.existsByModelAndName(gymMachine.getModel(), gymMachine.getName())) {

            log.error("ERROR: La máquina ya está registrada en el sistema con el mismo nombre y modelo.");
            throw new ValidationException(" La máquina ya está registrada en el sistema con el mismo nombre y modelo.");

        }
    }


    /**
     * Modifica una máquina de entrenamimento
     *
     * @param gymMachine
     */
    @Override
    public void updateGymMachine(GymMachine gymMachine) {

        // comprobamos que se encuentra en la BB DD
        log.info("Comprobamos en el sistema que existe la máquina  de entrenamiento. ");
        if (gymMachineRepository.existsById(gymMachine.getId())) {

            log.info("Existe la maquina de entrenamiento en el sistema.");
            // insertamos nuevo

            gymMachine.setLastUpdateDate(LocalDateTime.now());

            gymMachineRepository.save(gymMachine);
            log.info("OK: Máquina de entrenamiento actualizado con éxito.");

        } else {
            log.error("No se encuentra la maquina de entrenamiento que quieres modificar - GymMachine: {} .", gymMachine);
            throw new GymMachineNotFoundException("No se encuentra la máquina de entrenamiento que quieres modificar");
        }

    }

    @Override
    public void deleteAllGymMachine() {

        log.info("Procedemos a vaciar la BB DD de maquinas en el sistema...");
        gymMachineRepository.deleteAll();
        log.info("OK: Se ha borrado todas las maquinas de la BB DD correctamente...");

    }

    /**
     * Borra una máquina de entrenamiento por su id
     *
     * @param idGymMachine
     */
    @Override
    public void deleteGymMachine(String idGymMachine) {

        log.info("Checking for the existence of GymMachine with ID: {}", idGymMachine);
        Optional<GymMachine> gymMachineToDelete = gymMachineRepository.findById(idGymMachine);

        gymMachineToDelete.ifPresentOrElse(gymMachine -> {
                    log.info("GymMachine found, proceeding with deletion.");
                    deleteGymMachineInTrainings(gymMachine);
                    gymMachineRepository.deleteById(idGymMachine);
                    log.info("GymMachine with ID: {} successfully deleted.", idGymMachine);
                }, () -> {
                    log.error("GymMachine with ID: {} not found.", idGymMachine);
                    throw new GymMachineNotFoundException("GymMachine with ID " + idGymMachine + " not found in the system.");
                }

        );

    }

    /**
     * Borra de la tabla de entrenamiento aquellos entrenamientos
     * que contenga dicho entrenamiento que vamos a eliminar
     */
    private void deleteGymMachineInTrainings(GymMachine gymMachineDelete) {
        log.info("Removing GymMachine with id: {} from all trainings", gymMachineDelete.getId());
        List<Training> affectedTrainings = trainingRepository.findByGymMachine(gymMachineDelete);

        affectedTrainings.forEach(training -> {
            training.setGymMachine(null); // Directly remove the reference
            log.debug("Removed GymMachine from Training: {}", training); // Consider using debug level
        });

        trainingRepository.saveAll(affectedTrainings);
        log.info("Successfully removed GymMachine from all affected trainings");
    }

}
