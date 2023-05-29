package com.uma.gymfit.trainingtable.service.impl;

import com.uma.gymfit.trainingtable.model.training.TrainingTable;
import com.uma.gymfit.trainingtable.model.user.User;
import com.uma.gymfit.trainingtable.repository.ITrainingTableRepository;
import com.uma.gymfit.trainingtable.repository.IUserRepository;
import com.uma.gymfit.trainingtable.service.ITrainingTableService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
@Slf4j
public class TrainingTableService implements ITrainingTableService {

    @Autowired
    private ITrainingTableRepository trainingTableRepository;

    @Autowired
    private IUserRepository userRepository;


    /**
     * Devuelve todas las tablas almacenadas en BBDD
     *
     * @return List<TrainingTable>
     */
    @Override
    public List<TrainingTable> allTrainingTable() {
        return trainingTableRepository.findAll();
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

            TrainingTable trainingTable = trainingTableRepository.findById(idTrainingTable).get();

            checkUserInRepository(trainingTable);

            log.info("OK: TrainingTable encontrado.....");
            return trainingTable;
        }

        log.error("ERROR: TrainingTable no se encuentra en el sistema...");
        throw new Exception("ERROR: TrainingTable no se encuentra en el sistema...");

    }

    /**
     * Comprueba que existe el usuario que tiene asignado en el sistema,
     * en el caso de no existir lo elimina
     *
     * @param trainingTable
     */
    private void checkUserInRepository(TrainingTable trainingTable) {

        User userTrainingTable = trainingTable.getUser();

        log.info("En el caso de que tenga un usuario asignado comprobamos que existe en el sistema");

        if (userTrainingTable != null) {

            log.info("OK: Tiene un usuario asignado y comprobamos que existe el usuario en el sistema");
            Optional<User> userInRepository = userRepository.findById(userTrainingTable.getId());

            if (userInRepository.isEmpty()) {
                log.info("OK: No existe dicho usuario, asi que procedemos a eliminarlo...");
                trainingTable.setUser(null);
                trainingTableRepository.save(trainingTable);
            }

        }

        log.info("OK: Terminamos el proceso de comprobar si el usuario existe en el sistema con éxito....");
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

    }


    @Override
    public List<TrainingTable> findByTrainingType(String typeTraining, String idUser) throws Exception {

        User user = null;
        List<TrainingTable> trainingTableList;
        log.info("Buscamos el usuario en el sistema....");
        if (userRepository.existsById(idUser)) {
            log.info("OK: User encontrado.....");
            user = userRepository.findById(idUser).get();
        }

        if (user == null) {
            log.error("ERROR: El usuario no se encuentra en el sistema...");
            throw new Exception("ERROR: User no se encuentra en el sistema...");
        } else {

            log.info("Buscamos las tablas asignadas al siguiente usuario: {}", user);
            List<TrainingTable> listTrainingTablesByUser = trainingTableRepository.findByUser(user);

            log.info("Las tablas asignadas son: {} ", listTrainingTablesByUser);

            log.info("Buscamos la(s) tabla(s) de entrenamiento según su tipo: {}", typeTraining);

            trainingTableList = listTrainingTablesByUser.stream().filter(trainingTable -> {
                return trainingTable.getTypeTraining().equals(typeTraining);
            }).collect(Collectors.toList());

            log.info("OK: Las tablas encontradas son: {}", trainingTableList);

            return trainingTableList;

        }


    }

    @Override
    public List<TrainingTable> findByUser(User user) {
        log.info("Buscamos tablas de entrenamiento del siguiente usuario: {} ", user);
        return trainingTableRepository.findByUser(user);
    }

}
