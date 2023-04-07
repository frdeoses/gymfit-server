package com.uma.gymfit.trainingtable.service.impl;

import com.uma.gymfit.trainingtable.model.training.GymMachine;
import com.uma.gymfit.trainingtable.model.training.Training;
import com.uma.gymfit.trainingtable.repository.IGymMachineRepository;
import com.uma.gymfit.trainingtable.repository.ITrainingRepository;
import com.uma.gymfit.trainingtable.service.IGymMachineService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Service
@Slf4j
public class GymMachineService implements IGymMachineService {

    @Autowired
    private IGymMachineRepository gymMachineRepository;

    @Autowired
    private ITrainingRepository trainingRepository;

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

    /**
     * Crea  una maquina
     *
     * @param gymMachine
     */
    @Override
    public void createGymMachine(GymMachine gymMachine) throws Exception {

        //en caso de no tener problemas guardaremos en el repositorio.
        log.info("Procedemos a guardar en el sistema la siguiente maquina: {}.", gymMachine);
        if (gymMachine != null && gymMachine.getId() != null && !gymMachine.getId().isEmpty() && gymMachineRepository.existsById(gymMachine.getId())) {
            log.error("ERROR: La maquina introducida no es valida o ya esta registrada en el sistema...");
            throw new Exception("ERROR: La maquina introducida no es valida o ya esta registrada en el sistema...");
        }

        // reviso si esta el modelo ya en el sistema
        if (gymMachineRepository.existsByModel(gymMachine.getModel())) {

            GymMachine gymMachineModelSave = gymMachineRepository.findByModel(gymMachine.getModel());

            // en el caso de que ya este la maquina , incremento el numero
            if (gymMachine.getName().equals(gymMachineModelSave.getName()) && gymMachineModelSave.getModel().equals(gymMachine.getModel())) {
                gymMachineModelSave.setNumMachine(gymMachine.getNumMachine() > 0 ? gymMachineModelSave.getNumMachine() + gymMachine.getNumMachine() : gymMachineModelSave.getNumMachine() + 1);
                gymMachineRepository.save(gymMachineModelSave);

                // si no lo que hago es que me la vuelvo a crear de nuevo
            } else {
                gymMachine.setId(UUID.randomUUID().toString());
                gymMachine.setNumMachine(gymMachine.getNumMachine() > 0 ? gymMachine.getNumMachine() : gymMachine.getNumMachine() + 1);
                gymMachineRepository.save(gymMachine);
            }

            log.info("OK: Máquina de entrenamiento guardado con éxito.");

            // en el caso de que el modelo no este en el sistema lo creo de nuevo
        } else {
            gymMachine.setId(UUID.randomUUID().toString());
            gymMachine.setNumMachine(gymMachine.getNumMachine() > 0 ? gymMachine.getNumMachine() : gymMachine.getNumMachine() + 1);
            gymMachineRepository.save(gymMachine);

            log.info("OK: Máquina de entrenamiento guardado con éxito.");
        }

    }

    /**
     * Modifica una máquina de entrenamimento
     *
     * @param gymMachine
     */
    @Override
    public void updateGymMachine(GymMachine gymMachine) throws Exception {

        // comprobamos que se encuentra en la BBDD
        log.info("Comprobamos en el sistema que existe la máquina  de entrenamiento. ");
        if (gymMachineRepository.existsById(gymMachine.getId())) {

            log.info("Existe la maquina de entrenamiento en el sistema.");
            // insertamos nuevo
            gymMachineRepository.save(gymMachine);
            log.info("OK: Máquina de entrenamiento actualizado con éxito.");

        } else {
            log.error("No se encuentra la maquina de entrenamiento que quieres modificar");
            throw new Exception("No se encuentra la máquina de entrenamiento que quieres modificar");
        }

    }

    /**
     * Borra una máquina de entrenamiento por su id
     *
     * @param idGymMachine
     */
    @Override
    public void deleteGymMachine(String idGymMachine) throws Exception {

        //comprobamos que el id se encuentra en el repositorio
        log.info("Comprobamos en el sistema que existe la maquina de entrenamiento. ");
        if (gymMachineRepository.existsById(idGymMachine)) {
            log.info("Existe la maquina de entrenamiento en el sistema.");

            GymMachine gymMachineDelete = gymMachineRepository.findById(idGymMachine).get();

            findGymMachineInTrainings(gymMachineDelete);

            //una vez este todo correcto borramos el dato.
            gymMachineRepository.deleteById(idGymMachine);
            log.info("OK: Maquina de entrenamiento eliminada con éxito.");

        } else {
            log.error("La máquina que quiere eliminar no se encuentra en el sistema.");
            throw new Exception("La maquina que quiere eliminar no se encuentra en el sistema.");
        }

    }

    private void findGymMachineInTrainings(GymMachine gymMachine){

        log.info("Buscamos en el sistema si existe alguna maquina asociada a algún entrenamiento.");

        if(trainingRepository.existsByGymMachine(gymMachine)){
            log.info("OK: Existe entrenamientos que contienen dicha maquina que quieres eliminar.");
            List<Training> trainings = trainingRepository.findByGymMachine(gymMachine);
            trainings.stream().forEach(training -> {
                if(training.getGymMachine().getId().equals(gymMachine.getId())){
                    training.setGymMachine(null);
                    trainingRepository.save(training);
                }
            });
            log.info("OK: Se han eliminado con éxito las maquinas de los {} entrenamientos",trainings.size());
        }

    }
}
