package com.uma.gymfit.trainingtable.service.impl;

import com.uma.gymfit.trainingtable.exception.training.TrainingNotFoundException;
import com.uma.gymfit.trainingtable.model.dtos.NewWorkedWeight;
import com.uma.gymfit.trainingtable.model.training.Training;
import com.uma.gymfit.trainingtable.model.training.TrainingTable;
import com.uma.gymfit.trainingtable.model.training.TrainingType;
import com.uma.gymfit.trainingtable.model.user.User;
import com.uma.gymfit.trainingtable.repository.ITrainingRepository;
import com.uma.gymfit.trainingtable.repository.ITrainingTableRepository;
import com.uma.gymfit.trainingtable.repository.IUserRepository;
import com.uma.gymfit.trainingtable.service.ITrainingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.uma.gymfit.trainingtable.utils.Literals.NOT_FOUND_TRAINING_MSG;
import static com.uma.gymfit.trainingtable.utils.TrainingTrace.logErrorNotFoundTraining;
import static com.uma.gymfit.trainingtable.utils.TrainingTrace.logUpdateTraining;


@Service
@Slf4j
public class TrainingService implements ITrainingService {

    private final ITrainingRepository trainingRepository;

    private final ITrainingTableRepository trainingTableRepository;

    private final IUserRepository userRepository;

    @Autowired
    public TrainingService(final ITrainingRepository trainingRepository,
                           final ITrainingTableRepository trainingTableRepository,
                           final IUserRepository userRepository) {
        this.trainingRepository = trainingRepository;
        this.trainingTableRepository = trainingTableRepository;
        this.userRepository = userRepository;
    }


    /**
     * Devuelve todas las tablas almacenadas en BB DD
     *
     * @return List<TrainingTable>
     */
    @Override
    @Transactional(readOnly = true)
    public TrainingType[] trainingType() {
        return TrainingType.values();
    }

    /**
     * Devuelve todos los ejercicios almacenados en BB DD
     *
     * @return List<TrainingTable>
     */
    @Override
    @Transactional(readOnly = true)
    public List<Training> allTraining() {
        return trainingRepository.findAll();
    }

    /***
     * Devuelve la tabla almacenada en BB DD
     * @param idTraining
     * @return
     * @
     */
    @Override
    @Transactional(readOnly = true)
    public Training findTraining(String idTraining) {

        log.info("Buscamos el ejercicio en el sistema...");

        return trainingRepository.findById(idTraining)
                .orElseThrow(() -> {
                    log.error("El ejercicio que quiere encontrar no se encuentra en el sistema - ID: {}.", idTraining);
                    return new TrainingNotFoundException("ERROR: El ejercicio no se encuentra en el sistema.");
                });

    }


    /**
     * Crea un Ejercicio
     *
     * @param training
     */
    @Override
    @Transactional
    public void createTraining(Training training) {

        try {
            //en caso de no tener problemas guardaremos en el repositorio.
            log.info("Procedemos a guardar en el sistema el siguiente ejercicio: {}.", training);
            training.setCreationDate(LocalDateTime.now());
            updateFields(training, training);
            trainingRepository.save(training);
            log.info("OK: Ejercicio guardado con éxito.");

        } catch (DataAccessException e) {
            log.error("ERROR: Error al guardar el entrenamiento en la base de datos - {}", e.getMessage());
            throw new TrainingNotFoundException("Error al guardar el entrenamiento en la base de datos.");
        }
    }

    /**
     * Borra un ejercicio por su id
     *
     * @param idTraining
     */
    @Override
    @Transactional
    public void deleteTraining(String idTraining) {

        //comprobamos que el ID se encuentra en el repositorio
        log.info("Comprobamos en el sistema que existe el ejercicio. ");

        trainingRepository.findById(idTraining).ifPresentOrElse(training -> {

            deleteTrainingInTrainingTable(training);

            trainingRepository.deleteById(idTraining);
            log.info("OK: Ejercicio eliminado con éxito.");


        }, () -> {
            log.error("El ejercicio que quiere eliminar no se encuentra en el sistema - ID: {}.", idTraining);
            throw new TrainingNotFoundException("El ejercicio que quiere eliminar no se encuentra en el sistema.");
        });

    }

    /**
     * Borra de la tabla de entrenamiento aquellos entrenamientos
     * que contenga dicho entrenamiento que vamos a eliminar
     *
     * @param trainingDelete
     */
    private void deleteTrainingInTrainingTable(Training trainingDelete) {

        log.info("Buscando tablas de entrenamiento que contengan el entrenamiento ID: {}", trainingDelete.getId());

        // Suponiendo que se ha implementado findByTrainingsId en el repositorio
        List<TrainingTable> affectedTables = trainingTableRepository.findByTrainingsId(trainingDelete.getId());

        List<TrainingTable> updatedTables = affectedTables.stream()
                .filter(table -> table.getListTraining() != null && table.getListTraining().removeIf(training -> training.getId().equals(trainingDelete.getId())))
                .collect(Collectors.toList());

        if (!updatedTables.isEmpty()) {
            trainingTableRepository.saveAll(updatedTables);
            log.info("Tablas de entrenamiento actualizadas con éxito después de eliminar el entrenamiento ID: {}", trainingDelete.getId());
        } else {
            log.info("No se encontraron tablas de entrenamiento que contuvieran el entrenamiento ID: {}", trainingDelete.getId());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Training> findTrainingsByUser(String userId) {
        log.info("Buscamos los entrenamiento del siguiente usuario: {} ", userId);

        return trainingRepository.findByUserId(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Training> findTrainingsByTrainingType(String typeTraining, String idUser) {

        log.info("Buscando entrenamientos para el usuario con ID: {} y tipo de entrenamiento: {}", idUser, typeTraining);

        User user = userRepository.findById(idUser)
                .orElseThrow(() -> {
                    log.error("Error: El usuario con ID {} no se encuentra en el sistema.", idUser);
                    return new UsernameNotFoundException("Error: El usuario con ID " + idUser + " no se encuentra en el sistema.");
                });

        log.info("Usuario encontrado: {}", user);

        List<Training> trainingListByUser = trainingRepository.findByUserId(idUser);

        log.info("Entrenamientos asignados al usuario: {}", trainingListByUser);

        List<Training> filteredTrainings = trainingListByUser.stream()
                .filter(training -> training.getTypeTraining().equals(typeTraining))
                .collect(Collectors.toList());

        log.info("Entrenamientos filtrados por tipo {}: {}", typeTraining, filteredTrainings);

        return filteredTrainings;
    }


    /**
     * Modifica un ejercicio
     *
     * @param training
     */
    @Override
    @Transactional
    public void updateTraining(Training training) {

        log.info("Intentando actualizar el entrenamiento con ID: {}", training.getId());

        trainingRepository.findById(training.getId()).ifPresentOrElse(
                trainingSave -> {

                    Training trainingUpdateFields = updateFields(trainingSave, training);

                    trainingRepository.save(trainingUpdateFields);
                    logUpdateTraining(training.getId());
                }, () -> {
                    logErrorNotFoundTraining(training.getId());

                    throw new TrainingNotFoundException(NOT_FOUND_TRAINING_MSG + training.getId());
                }
        );

    }

    @Override
    @Transactional
    public void addNewWorkedWeights(NewWorkedWeight workedWeight) {

        log.info("Procedemos a guardar el siguiente peso {}, en el siguiente entrenamiento con id: {} ", workedWeight.getWorkedWeight(), workedWeight.getTrainingId());

        trainingRepository.findById(workedWeight.getTrainingId()).ifPresentOrElse(training -> {

            log.info("Entrenamiento con ID: {} encontrado.", training.getId());

            if (Objects.isNull(training.getListWorkedWeights()) || training.getListWorkedWeights().isEmpty())
                training.setListWorkedWeights(new ArrayList<>());

            training.getListWorkedWeights().add(workedWeight.getWorkedWeight());

            trainingRepository.save(training);

            logUpdateTraining(training.getId());
            log.info("Entrenamiento : {} .", training);

        }, () -> {
            logErrorNotFoundTraining(workedWeight.getTrainingId());
            throw new TrainingNotFoundException("No se encuentra el entrenamiento con ID: " + workedWeight.getTrainingId());
        });

    }


    private Training updateFields(Training trainingSave, Training training) {
        return trainingSave.toBuilder()
                .lastUpdateDate(LocalDateTime.now())
                .userId(training.getUserId())
                .like(training.getLike())
                .description(training.getDescription())
                .name(training.getName())
                .exercisedArea(training.getExercisedArea())
                .gymMachine(training.getGymMachine())
                .caloriesBurned(training.getCaloriesBurned())
                .needBeSupervised(training.isNeedBeSupervised())
                .numSeries(training.getNumSeries())
                .listWorkedWeights(training.getListWorkedWeights())
                .typeTraining(training.getTypeTraining())
                .build();

    }
}
