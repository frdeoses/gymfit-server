package com.uma.gymfit.trainingtable.service.impl;

import com.uma.gymfit.trainingtable.model.training.TrainingTable;
import com.uma.gymfit.trainingtable.repository.IRepositorioTabla;
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
    private IRepositorioTabla repositorioTabla;


    /**
     * Devuelve todas las tablas almacenadas en BBDD
     *
     * @return List<TrainingTable>
     */
    @Override
    public List<TrainingTable> allTrainingTable() {
        return repositorioTabla.findAll();
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
        if (repositorioTabla.existsById(idTrainingTable)) {
            log.info("OK: TrainingTable encontrado.....");
            return repositorioTabla.findById(idTrainingTable).get();
        }

        log.error("ERROR: TrainingTable no se encuentra en el sistema...");
        throw new Exception("ERROR: TrainingTable no se encuentra en el sistema...");

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
        repositorioTabla.save(trainingT);
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
        if (repositorioTabla.existsById(id)) {
            log.info("Exite la tabla de entrenamiento en el sistema.");
            //una vez este todo correcto borramos el dato.
            repositorioTabla.deleteById(id);
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
        if (repositorioTabla.existsById(trainingT.getId())) {

            // Borramos antrior user (se supone que el save ya lo hace)
//            repositorioTabla.deleteById(trainingT.getId());
            log.info("Exite la tabla de entrenamiento en el sistema.");
            // insertamos nuevo
            repositorioTabla.save(trainingT);
            log.info("OK: Tabla de entrenamiento guardado con exito.");

        } else {
            log.error("No se encuentra la tabla de entrenamiento que quieres modificar");
            throw new Exception("No se encuentra la tabla que quieres modificar");
        }

    }
}
