package com.uma.gymfit.trainingtable.service.impl;

import com.uma.gymfit.trainingtable.exception.training.TrainingNotFoundException;
import com.uma.gymfit.trainingtable.model.training.Training;
import com.uma.gymfit.trainingtable.model.training.TrainingTable;
import com.uma.gymfit.trainingtable.model.training.TrainingType;
import com.uma.gymfit.trainingtable.model.user.User;
import com.uma.gymfit.trainingtable.repository.ITrainingRepository;
import com.uma.gymfit.trainingtable.repository.ITrainingTableRepository;
import com.uma.gymfit.trainingtable.repository.IUserRepository;
import com.uma.gymfit.trainingtable.service.ITrainingService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
@Slf4j
public class TrainingService implements ITrainingService {

    private final ITrainingRepository trainingRepository;

    private final ITrainingTableRepository trainingTableRepository;

    private final IUserRepository userRepository;

    /**
     * Devuelve todas las tablas almacenadas en BB DD
     *
     * @return List<TrainingTable>
     */
    @Override
    public TrainingType[] trainingType() {
        return TrainingType.values();
    }

    /**
     * Devuelve todos los ejercicios almacenados en BB DD
     *
     * @return List<TrainingTable>
     */
    @Override
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
    public Training findTraining(String idTraining) {

        log.info("Buscamos el ejercicio en el sistema...");
        if (trainingRepository.existsById(idTraining)) {
            log.info("OK: Ejercicio encontrada.....");

            Optional<Training> trainingSave = trainingRepository.findById(idTraining);

            if (trainingSave.isEmpty()) {
                log.error("El ejercicio que quiere encontrar no se encuentra en el sistema - ID: {}.", idTraining);
                throw new TrainingNotFoundException("ERROR: El ejercicio no se encuentra en el sistema.");
            }

            Training training = trainingSave.get();

            checkUser(training);

            return training;
        }

        log.error("ERROR: El ejercicio no se encuentra en el sistema - ID: {}. ", idTraining);
        throw new TrainingNotFoundException("ERROR: El ejercicio no se encuentra en el sistema.");

    }

    /**
     * Comprueba que existe el usuario que tiene asignado en el sistema,
     * en el caso de no existir lo elimina
     *
     * @param training
     */
    private void checkUser(Training training) {

        log.info("En el caso de que tenga un usuario asignado comprobamos que existe en el sistema");
        User userTraining = training.getUser();

        log.info("OK: Tiene un usuario asignado y comprobamos que existe el usuario en el sistema");
        Optional<User> userInRepository = userRepository.findById(userTraining.getId());

        if (userInRepository.isEmpty()) {
            log.warn("OK: No existe dicho usuario, asi que procedemos a eliminarlo...");
            training.setUser(null);
            trainingRepository.save(training);
        }

        log.info("OK: Terminamos el proceso de comprobar si el usuario existe en el sistema con éxito....");
    }

    /**
     * Crea un Ejercicio
     *
     * @param training
     */
    @Override
    public void createTraining(Training training) {

        try {
            //en caso de no tener problemas guardaremos en el repositorio.
            log.info("Procedemos a guardar en el sistema el siguiente ejercicio: {}.", training);
            if (Objects.isNull(training.getUser()) || !userRepository.existsById(training.getUser().getId())) {
                log.error("Error: El usuario asignado al siguiente entrenamiento no se encuentra en el sistema - Training: {}", training);
                throw new UsernameNotFoundException("Error: El usuario asignado al siguiente entrenamiento no se encuentra en el sistema");
            }

            if (Strings.isEmpty(training.getId()))
                training.setId(UUID.randomUUID().toString());
            training.setCreationDate(LocalDateTime.now());
            training.setLastUpdateDate(LocalDateTime.now());
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
    public void deleteTraining(String idTraining) {

        //comprobamos que el ID se encuentra en el repositorio
        log.info("Comprobamos en el sistema que existe el ejercicio. ");
        if (trainingRepository.existsById(idTraining)) {
            log.info("Existe el ejercicio en el sistema.");

            Optional<Training> trainingDeleteSave = trainingRepository.findById(idTraining);

            if (trainingDeleteSave.isEmpty()) {
                log.error("El ejercicio que quiere eliminar no se encuentra en el sistema - ID: {}.", idTraining);
                throw new TrainingNotFoundException("El ejercicio que quiere eliminar no se encuentra en el sistema.");
            }

            Training trainingDelete = trainingDeleteSave.get();

            deleteTrainingInTrainingTable(trainingDelete);

            //una vez este correcto borramos el dato.
            trainingRepository.deleteById(idTraining);
            log.info("OK: Ejercicio eliminado con éxito.");

        } else {
            log.error("El ejercicio que quiere eliminar no se encuentra en el sistema - ID: {}.", idTraining);
            throw new TrainingNotFoundException("El ejercicio que quiere eliminar no se encuentra en el sistema.");
        }

    }

    /**
     * Borra de la tabla de entrenamiento aquellos entrenamientos
     * que contenga dicho entrenamiento que vamos a eliminar
     *
     * @param trainingDelete
     */
    private void deleteTrainingInTrainingTable(Training trainingDelete) {

        log.info("Buscamos en el sistema si alguna tabla de entrenamiento contiene el entrenamiento que vamos a borrar");
        List<TrainingTable> trainingTableList = trainingTableRepository.findAll();

        trainingTableList.forEach(trainingTable -> {

            boolean isTrainingDelete = trainingTable.getListTraining().removeIf(training -> training.getId().equals(trainingDelete.getId()));

            if (isTrainingDelete) {
                log.warn("OK: Se procede a borrar dicho entrenamiento de la tabla: {}", trainingTable);
                trainingTableRepository.save(trainingTable);
            }

        });
        log.info("OK: Finalizado con éxito el proceso de borrado...");
    }

    @Override
    public List<Training> findTrainingsByUser(User user) {
        log.info("Buscamos los entrenamiento del siguiente usuario: {} ", user);
        if (!userRepository.existsById(user.getId())) {
            log.error("No se encuentra el usuario - User: {}.", user);
            throw new TrainingNotFoundException("No se encuentra el usuario.");
        }

        return trainingRepository.findByUser(user);
    }

    @Override
    public List<Training> findTrainingsByTrainingType(String typeTraining, String idUser) {

        log.info("Buscamos en el sistema el siguiente id de usuario: {}", idUser);

        if (!userRepository.existsById(idUser)) {
            log.error("Error: El usuario introducido es erróneo o no se encuentra en el sistema - ID: {}.", idUser);
            throw new UsernameNotFoundException("Error: El usuario introducido es erróneo o no se encuentra en el sistema.");
        }

        Optional<User> userSave = userRepository.findById(idUser);

        if (userSave.isEmpty()) {
            log.error("No se encuentra el usuario");
            throw new UsernameNotFoundException("No se encuentra el usuario a partir del tipo de entrenamiento.");
        }

        User user = userSave.get();

        log.info("OK: Usuario encontrado: {}", user);

        List<Training> trainingListByUser = trainingRepository.findByUser(user);

        log.info("Los entrenamientos asignados al usuario son: {} ", trainingListByUser);

        log.info("Buscamos la(s) tabla(s) de entrenamiento según su tipo: {}", typeTraining);

        List<Training> trainingList = trainingListByUser.stream().filter(training -> training.getTypeTraining().equals(typeTraining)).collect(Collectors.toList());

        log.info("OK: Los entrenamientos encontrados son: {}", trainingList);

        return trainingList;
    }

    /**
     * Modifica un ejercicio
     *
     * @param training
     */
    @Override
    public void updateTraining(Training training) {

        // comprobamos que se encuentra en la BB DD
        log.info("Comprobamos en el sistema que existe el ejercicio. ");
        if (trainingRepository.existsById(training.getId())) {

            training.setLastUpdateDate(LocalDateTime.now());

            log.info("Existe el ejercicio en el sistema.");
            // insertamos nuevo
            trainingRepository.save(training);
            log.info("OK: Ejercicio actualizado con éxito.");

        } else {
            log.error("No se encuentra el ejercicio que quieres modificar");
            throw new TrainingNotFoundException("No se encuentra el ejercicio que quieres modificar");
        }

    }
}
