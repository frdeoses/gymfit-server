package com.uma.gymfit.trainingtable.service.impl;

import com.uma.gymfit.trainingtable.exception.table.TrainingTableFindException;
import com.uma.gymfit.trainingtable.exception.table.TrainingTableNotFoundException;
import com.uma.gymfit.trainingtable.model.training.Training;
import com.uma.gymfit.trainingtable.model.training.TrainingTable;
import com.uma.gymfit.trainingtable.model.user.User;
import com.uma.gymfit.trainingtable.repository.ITrainingTableRepository;
import com.uma.gymfit.trainingtable.repository.IUserRepository;
import com.uma.gymfit.trainingtable.service.ITrainingTableService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
@Slf4j
public class TrainingTableService implements ITrainingTableService {

    private final ITrainingTableRepository trainingTableRepository;

    private final IUserRepository userRepository;


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
     * @param idTrainingTable
     * @return
     */
    @Override
    public TrainingTable findTrainingTable(String idTrainingTable) {

        log.info("Buscamos la Tabla en el sistema...");
        if (trainingTableRepository.existsById(idTrainingTable)) {

            Optional<TrainingTable> trainingTableSave = trainingTableRepository.findById(idTrainingTable);

            if (trainingTableSave.isPresent()) {
                TrainingTable trainingTable = trainingTableSave.get();
                checkUserInRepository(trainingTable);

                log.info("OK: TrainingTable encontrado.....");
                return trainingTable;
            }

            log.error("ERROR: La tabla no se ha guardado correctamente...");
            throw new TrainingTableFindException("ERROR: La tabla no se ha guardado correctamente...");


        }

        log.error("ERROR: TrainingTable no se encuentra en el sistema...");
        throw new TrainingTableNotFoundException("ERROR: TrainingTable no se encuentra en el sistema...");

    }

    /**
     * Comprueba que existe el usuario que tiene asignado en el sistema,
     * en el caso de no existir lo elimina
     *
     * @param trainingTable
     */
    private void checkUserInRepository(TrainingTable trainingTable) {

        String userIdTrainingTable = trainingTable.getUserId();

        if (Objects.nonNull(userIdTrainingTable)) {
            Optional<User> userTrainingTable = userRepository.findById(userIdTrainingTable);

            log.info("En el caso de que tenga un usuario asignado comprobamos que existe en el sistema");

            if (userTrainingTable.isEmpty()) {

                log.info("OK: No existe dicho usuario, asi que procedemos a eliminarlo...");
                trainingTable.setUserId(null);
                trainingTableRepository.save(trainingTable);

            }
        }


        log.info("OK: Terminamos el proceso de comprobar si el usuario existe en el sistema con éxito....");
    }


    /**
     * Crea una tabla de entrenamiento
     *
     * @param trainingT
     */
    @Override
    public void createTrainingTable(TrainingTable trainingT) {

        try {
            //en caso de no tener problemas guardaremos en el repositorio.
            log.info("Procedemos a guardar en el sistema la siguiente tabla de entrenamiento: {}.", trainingT);

            if (Strings.isEmpty(trainingT.getId()))
                trainingT.setId(UUID.randomUUID().toString());

            if (Objects.nonNull(trainingT.getListTraining())) {
                trainingT.setCaloriesBurned(calculateCalories(trainingT.getListTraining()));
            }
            trainingT.setCreationDate(LocalDateTime.now());
            trainingT.setLastUpdateDate(LocalDateTime.now());

            trainingTableRepository.save(trainingT);
            log.info("OK: Tabla de entrenamiento guardado con éxito.");
        } catch (DataAccessException e) {

            log.error("ERROR: Error al guardar la tabla de entrenamiento en la base de datos - {}", e.getMessage());
            throw new TrainingTableNotFoundException("Error al crear la tabla de entrenamiento en la base de datos.");
        }

    }

    private int calculateCalories(List<Training> listTraining) {
        return listTraining.stream()
                .mapToInt(Training::getCaloriesBurned)
                .sum();
    }

    /**
     * Borra una tabla de entrenamiento por su id
     *
     * @param id
     */
    @Override
    public void deleteTrainingTable(String id) {


        //comprobamos que el ID se encuentra en el repositorio
        log.info("Comprobamos en el sistema que existe la tabla de entrenamiento. ");
        if (trainingTableRepository.existsById(id)) {
            log.info("Existe la tabla de entrenamiento en el sistema.");
            //una vez este correcto borramos el dato.
            trainingTableRepository.deleteById(id);
            log.info("OK: Tabla de entrenamiento eliminada con éxito.");

        } else {
            log.error("La tabla de entrenamiento que quiere eliminar no se encuentra en el sistema - ID: {} .", id);
            throw new TrainingTableNotFoundException("La tabla de entrenamiento que quiere eliminar no se encuentra en el sistema.");
        }

    }


    /**
     * Modifica una tabla de entrenamiento
     *
     * @param trainingT
     */
    @Override
    public void updateTrainingTable(TrainingTable trainingT) {

        // comprobamos que se encuentra en la BB DD
        log.info("Comprobamos en el sistema que existe la tabla de entrenamiento. ");
        if (trainingTableRepository.existsById(trainingT.getId())) {

            log.info("Existe la tabla de entrenamiento en el sistema.");
            // insertamos nuevo
            if (Objects.nonNull(trainingT.getListTraining()))
                trainingT.setCaloriesBurned(calculateCalories(trainingT.getListTraining()));
            trainingT.setLastUpdateDate(LocalDateTime.now());
            trainingTableRepository.save(trainingT);
            log.info("OK: Tabla de entrenamiento actualizado con éxito.");

        } else {
            log.error("No se encuentra la tabla de entrenamiento que quieres modificar");
            throw new TrainingTableNotFoundException("No se encuentra la tabla que quieres modificar");
        }

    }


    @Override
    public List<TrainingTable> findByTrainingType(String typeTraining, String idUser) {

        User user;
        List<TrainingTable> trainingTableList;
        log.info("Buscamos el usuario en el sistema....");

        if (!userRepository.existsById(idUser)) {

            log.error("ERROR: El usuario no se encuentra en el sistema...");
            throw new UsernameNotFoundException("ERROR: User no se encuentra en el sistema...");

        } else {
            Optional<User> userSave = userRepository.findById(idUser);

            if (userSave.isPresent()) {
                log.info("OK: User encontrado.....");
                user = userSave.get();
            } else {
                log.error("Error: Usuario no encontrado en el sistema - User: {}", userSave);
                throw new UsernameNotFoundException("Error: Usuario no encontrado en el sistema...");
            }

            log.info("Buscamos las tablas asignadas al siguiente usuario: {}", user);
            List<TrainingTable> listTrainingTablesByUser = findByUser(user.getId());

            log.info("Las tablas asignadas son: {} ", listTrainingTablesByUser);

            log.info("Buscamos la(s) tabla(s) de entrenamiento según su tipo: {}", typeTraining);

            trainingTableList = listTrainingTablesByUser.stream()
                    .filter(trainingTable ->
                            trainingTable.getTypeTraining().equals(typeTraining))
                    .collect(Collectors.toList());

            log.info("OK: Las tablas encontradas son: {}", trainingTableList);

            return trainingTableList;

        }


    }

    @Override
    public List<TrainingTable> findByUser(String userId) {
        log.info("Buscamos tablas de entrenamiento del siguiente usuario: {} ", userId);

        List<TrainingTable> trainingTableList = allTrainingTable();

        if (trainingTableList.isEmpty()) {
            log.info("No hay tablas de entrenamientos en el sistema...");
            return Collections.emptyList();
        }

        return trainingTableList.stream()
                .filter(Objects::nonNull)
                .filter(training -> Objects.equals(training.getUserId(), userId))
                .collect(Collectors.toList());
    }

}
