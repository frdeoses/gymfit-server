package com.uma.gymfit.trainingtable.service.impl;

import com.uma.gymfit.trainingtable.exception.table.TrainingTableCreateException;
import com.uma.gymfit.trainingtable.exception.table.TrainingTableNotFoundException;
import com.uma.gymfit.trainingtable.model.training.Training;
import com.uma.gymfit.trainingtable.model.training.TrainingTable;
import com.uma.gymfit.trainingtable.model.user.User;
import com.uma.gymfit.trainingtable.repository.ITrainingTableRepository;
import com.uma.gymfit.trainingtable.repository.IUserRepository;
import com.uma.gymfit.trainingtable.service.ITrainingTableService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static com.uma.gymfit.trainingtable.utils.Literals.NOT_FOUND_SYSTEM;

@Service
@Slf4j
public class TrainingTableService implements ITrainingTableService {

    private final ITrainingTableRepository trainingTableRepository;

    private final IUserRepository userRepository;

    @Autowired
    public TrainingTableService(final ITrainingTableRepository trainingTableRepository,
                                final IUserRepository userRepository) {
        this.trainingTableRepository = trainingTableRepository;
        this.userRepository = userRepository;
    }

    /**
     * Devuelve todas las tablas almacenadas en BB DD
     *
     * @return List<TrainingTable>
     */
    @Override
    public List<TrainingTable> allTrainingTable() {
        return trainingTableRepository.findAll();
    }

    /***
     * Devuelve la tabla almacenada en BB DD

     */
    @Override
    @Transactional
    public TrainingTable findTrainingTable(String idTrainingTable) {

        log.info("Buscamos la Tabla en el sistema...");

        TrainingTable trainingTable = trainingTableRepository.findById(idTrainingTable)
                .orElseThrow(() -> new TrainingTableNotFoundException("TrainingTable con ID: " + idTrainingTable + NOT_FOUND_SYSTEM));

        // Suponiendo que checkUserInRepository lanza una excepción si algo va mal
        checkUserInRepository(trainingTable);

        log.info("Tabla de entrenamiento encontrada: {}", trainingTable);
        return trainingTable;


    }

    /**
     * Comprueba que existe el usuario que tiene asignado en el sistema,
     * en el caso de no existir lo elimina
     */

    private void checkUserInRepository(TrainingTable trainingTable) {
        String userId = trainingTable.getUserId();

        if (Objects.nonNull(userId)) {
            log.info("Comprobando la existencia del usuario con ID: {} asociado a la tabla de entrenamiento", userId);

            userRepository.findById(userId).ifPresentOrElse(user -> log.info("Usuario con ID: {} existe y está asociado a la tabla de entrenamiento", userId), () -> {
                log.info("Usuario con ID: {} no encontrado. Eliminando referencia al usuario en la tabla de entrenamiento.", userId);
                // Considera si este comportamiento es realmente deseado o si debería manejarse de otra manera.
                removeUserFromTrainingTable(trainingTable);
            });
        }
    }

    private void removeUserFromTrainingTable(TrainingTable trainingTable) {
        trainingTable.setUserId(null);
        trainingTableRepository.save(trainingTable);
        log.info("Referencia al usuario eliminada de la tabla de entrenamiento.");
    }

    /**
     * Crea una tabla de entrenamiento
     */
    @Override
    @Transactional
    public void createTrainingTable(TrainingTable trainingT) {

        if (Objects.nonNull(trainingT.getListTraining())) {
            trainingT.setCaloriesBurned(calculateCalories(trainingT.getListTraining()));
        }

        try {
            trainingT.setCreationDate(LocalDateTime.now());
            trainingT.setLastUpdateDate(LocalDateTime.now());
            trainingTableRepository.save(trainingT);
            log.info("OK: Tabla de entrenamiento guardada con éxito.");
        } catch (DataAccessException e) {
            log.error("ERROR: Error al guardar la tabla de entrenamiento en la base de datos - {}", e.getMessage());
            throw new TrainingTableCreateException("Error al crear la tabla de entrenamiento en la base de datos.");
        }

    }

    private int calculateCalories(List<Training> listTraining) {
        return listTraining.stream()
                .mapToInt(Training::getCaloriesBurned)
                .sum();
    }

    /**
     * Borra una tabla de entrenamiento por su id
     */
    @Override
    public void deleteTrainingTable(String id) {


        //comprobamos que el ID se encuentra en el repositorio
        log.info("Comprobamos en el sistema que existe la tabla de entrenamiento. ");
        try {
            trainingTableRepository.deleteById(id);
            log.info("Tabla de entrenamiento con ID: {} eliminada con éxito.", id);
        } catch (EmptyResultDataAccessException e) {
            log.error("La tabla de entrenamiento con ID: {} no se encuentra en el sistema.Error: {}", id, e.getMessage());
            throw new TrainingTableNotFoundException("La tabla de entrenamiento con ID: " + id + NOT_FOUND_SYSTEM);
        }

    }


    /**
     * Modifica una tabla de entrenamiento
     */
    @Override
    public void updateTrainingTable(TrainingTable trainingT) {

        log.info("Intentando actualizar la tabla de entrenamiento con ID: {}", trainingT.getId());

        TrainingTable existingTrainingTable = trainingTableRepository.findById(trainingT.getId())
                .orElseThrow(() -> {
                    log.error("No se encuentra la tabla de entrenamiento con ID: {}", trainingT.getId());
                    return new TrainingTableNotFoundException("No se encuentra la tabla de entrenamiento con ID: " + trainingT.getId());
                });

        TrainingTable trainingTableUpdate = updateFields(trainingT, existingTrainingTable);

        trainingTableRepository.save(trainingTableUpdate);
        log.info("Tabla de entrenamiento con ID: {} actualizada con éxito.", trainingT.getId());


    }

    private TrainingTable updateFields(TrainingTable trainingT, TrainingTable existingTrainingTable) {
        return existingTrainingTable.toBuilder()
                .caloriesBurned(Objects.nonNull(trainingT.getListTraining()) ? calculateCalories(trainingT.getListTraining()) : existingTrainingTable.getCaloriesBurned())
                .userId(trainingT.getUserId())
                .breakTime(trainingT.getBreakTime())
                .name(trainingT.getName())
                .description(trainingT.getDescription())
                .observation(trainingT.getObservation())
                .listTraining(trainingT.getListTraining())
                .typeTraining(trainingT.getTypeTraining())
                .lastUpdateDate(LocalDateTime.now())
                // Añade aquí otros campos que necesites actualizar
                .build();
    }


    @Override
    public List<TrainingTable> findByTrainingType(String typeTraining, String idUser) {

        log.info("Buscando tablas de entrenamiento para el usuario con ID: {} y tipo de entrenamiento: {}", idUser, typeTraining);

        User user = userRepository.findById(idUser)
                .orElseThrow(() -> {
                    log.error("ERROR: Usuario con ID {} no se encuentra en el sistema.", idUser);
                    return new UsernameNotFoundException("ERROR: Usuario con ID " + idUser + NOT_FOUND_SYSTEM);
                });

        log.info("Usuario encontrado: {}", user);

        // Idealmente, este filtrado debe hacerse a nivel de base de datos/repositorio para mejorar la eficiencia
        List<TrainingTable> trainingTablesByType = trainingTableRepository.findByUserIdAndTypeTraining(idUser, typeTraining);

        log.info("Tablas de entrenamiento encontradas para el tipo {}: {}", typeTraining, trainingTablesByType);

        return trainingTablesByType;

    }

    @Override
    public List<TrainingTable> findByUser(String userId) {
        log.info("Buscando tablas de entrenamiento para el usuario: {}", userId);
        List<TrainingTable> trainingTables = trainingTableRepository.findByUserId(userId);

        if (trainingTables.isEmpty()) {
            log.info("No se encontraron tablas de entrenamiento para el usuario: {}", userId);
        } else {
            log.info("Se encontraron {} tablas de entrenamiento para el usuario: {}", trainingTables.size(), userId);
        }

        return trainingTables;

    }

}
