package com.uma.gymfit.trainingtable.service.impl;

import com.uma.gymfit.trainingtable.model.training.GymMachine;
import com.uma.gymfit.trainingtable.model.training.TrainingTable;
import com.uma.gymfit.trainingtable.repository.IGymMachineRepository;
import com.uma.gymfit.trainingtable.repository.ITrainingTableRepository;
import com.uma.gymfit.trainingtable.service.ITrainingTableService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
     * Devuelve todas las maquinas almacenadas en BBDD
     *
     * @return List<TrainingTable>
     */
    @Override
    public List<GymMachine> allGymMachine() {
        return gymMachineRepository.findAll();
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

        log.error("ERROR: La Maquina no se encuentra en el sistema...");
        throw new Exception("ERROR: La maquina no se encuentra en el sistema...");

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
     * Modifica una tabla de entrenamimento
     *
     * @param trainingT
     */
    @Override
    public void updateTrainingTable(TrainingTable trainingT) throws Exception {

        // comprobamos que se encuentra en la BBDD
        log.info("Comprobamos en el sistema que existe la tabla de entrenamiento. ");
        if (trainingTableRepository.existsById(trainingT.getId())) {

            // Borramos antrior user (se supone que el save ya lo hace)
//            repositorioTabla.deleteById(trainingT.getId());
            log.info("Exite la tabla de entrenamiento en el sistema.");
            // insertamos nuevo
            trainingTableRepository.save(trainingT);
            log.info("OK: Tabla de entrenamiento guardado con exito.");

        } else {
            log.error("No se encuentra la tabla de entrenamiento que quieres modificar");
            throw new Exception("No se encuentra la tabla que quieres modificar");
        }

    }
}
