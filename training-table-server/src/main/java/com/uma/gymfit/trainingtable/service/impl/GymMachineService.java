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
import org.apache.logging.log4j.util.Strings;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

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

        Optional<GymMachine> gymMachineOptional = gymMachineRepository.findById(idGymMachine);

        if (gymMachineOptional.isPresent()) {
            GymMachine user = gymMachineOptional.get();
            log.info("OK: Maquina encontrada - ID: {}", user.getId());
            return user;
        } else {
            log.error("ERROR: La maquina no se encuentra en el sistema - ID: {}", idGymMachine);
            throw new GymMachineNotFoundException("Maquina no se encuentra en el sistema - ID: " + idGymMachine);
        }


    }

    /**
     * Crea  una maquina
     *
     * @param gymMachine
     */
    @Override
    public void createGymMachine(GymMachine gymMachine) {

        if (Objects.isNull(gymMachine)) {
            throw new IllegalArgumentException("La máquina no puede ser null.");
        }

        log.info("Procedemos a guardar en el sistema la siguiente maquina: {}.", gymMachine);

        if (Strings.isEmpty(gymMachine.getId())) {
            // Solo generar un nuevo 'id' si es necesario.
            gymMachine.setId(UUID.randomUUID().toString());
        }

        if (gymMachineRepository.existsByModel(gymMachine.getModel())) {
            // Verifica si la máquina con el mismo modelo tiene el mismo 'id', lo cual indicaría un registro duplicado.
            GymMachine existingMachine = gymMachineRepository.findByModel(gymMachine.getModel());

            if (Objects.nonNull(existingMachine)
                    && existingMachine.getId().equals(gymMachine.getId())
                    || sameMachine(gymMachine.getName(), existingMachine.getName(), gymMachine.getModel(), existingMachine.getModel())) {
                log.error("ERROR: La máquina con 'id' {} ya está registrada en el sistema...", gymMachine.getId());
                throw new ValidationException("ERROR: La máquina ya está registrada en el sistema con el mismo 'id' o  con el mismo nombre y modelo.");
            }

            gymMachine = increaseNumberMachine(gymMachine, existingMachine);
        }

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

    private GymMachine increaseNumberMachine(GymMachine gymMachine, GymMachine gymMachineModelSave) {

        int newNumMachine = gymMachine.getNumMachine() > 0 ? gymMachine.getNumMachine() : 1;

        if (Objects.nonNull(gymMachineModelSave) && sameMachine(gymMachine.getName(), gymMachineModelSave.getName(), gymMachine.getModel(), gymMachineModelSave.getModel())) {
            newNumMachine += gymMachineModelSave.getNumMachine();
        }

        gymMachine.setNumMachine(newNumMachine);

        return gymMachine;

    }

    private boolean sameMachine(String nameMachine, String nameMachineSave, String modelMachine, String modelMachineSave) {
        return nameMachine.equals(nameMachineSave) && modelMachine.equals(modelMachineSave);
    }

    /**
     * Modifica una máquina de entrenamimento
     *
     * @param gymMachine
     */
    @Override
    public void updateGymMachine(GymMachine gymMachine) {

        // comprobamos que se encuentra en la BBDD
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

        //comprobamos que él, id se encuentra en el repositorio
        log.info("Comprobamos en el sistema que existe la maquina de entrenamiento. ");
        if (gymMachineRepository.existsById(idGymMachine)) {
            log.info("Existe la maquina de entrenamiento en el sistema.");

            Optional<GymMachine> gymMachineDelete = gymMachineRepository.findById(idGymMachine);

            if (gymMachineDelete.isPresent()) {
                deleteGymMachineInTrainings(gymMachineDelete.get());
                //una vez este correcto borramos el dato.
                gymMachineRepository.deleteById(idGymMachine);
                log.info("OK: Maquina de entrenamiento eliminada con éxito.");

            }


        } else {
            log.error("La máquina que quiere eliminar no se encuentra en el sistema - ID:{} .", idGymMachine);
            throw new GymMachineNotFoundException("La maquina que quiere eliminar no se encuentra en el sistema ");
        }

    }

    /**
     * Borra de la tabla de entrenamiento aquellos entrenamientos
     * que contenga dicho entrenamiento que vamos a eliminar
     */
    private void deleteGymMachineInTrainings(GymMachine gymMachineDelete) {

        log.info("Buscamos en el sistema si alguna tabla de entrenamiento contiene la maquina de entrenamiento que vamos a borrar");
        List<Training> trainingList = trainingRepository.findAll();

        trainingList.forEach(training -> {

            Optional<GymMachine> gymMachine = Optional.ofNullable(training.getGymMachine());

            if (gymMachine.isPresent()) {
                training.setGymMachine(gymMachine.get().getId().equals(gymMachineDelete.getId()) ? null : gymMachine.get());
                log.warn("OK: Se procede a borrar dicha maquina de entrenamiento del ejercicio: {}", training);
                trainingRepository.save(training);
            }

        });
        log.info("OK: Finalizado con éxito el proceso de borrado...");
    }

}
