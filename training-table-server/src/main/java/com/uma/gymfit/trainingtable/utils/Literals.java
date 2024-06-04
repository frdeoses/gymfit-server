package com.uma.gymfit.trainingtable.utils;

import com.uma.gymfit.trainingtable.model.ResponseHTTP;
import org.springframework.http.HttpStatus;

public class Literals {

    private Literals() {
        throw new UnsupportedOperationException("Esta es una clase de utilidad y no puede ser instanciada.");
    }

    public static final String API = "/api/gymfit";

    public static final String TRAINING_TABLES = "/training-tables";

    public static final String TYPE_TRAINING = "/training-tables/type-training";

    public static final String GYM_MACHINES = "/training-tables/gym-machines";

    public static final String TRAININGS = "/training-tables/trainings";

    public static final String TRAININGS_BY_USER = "/training-tables/trainings/users/{userId}";

    public static final String TRAININGS_BY_TYPE_TRAINING = "/training-tables/trainings/find-type-training";

    public static final String TRAINING_TABLE_ID = "/training-tables/{idTrainingTable}";

    public static final String TRAINING_TABLE_TYPE = "/training-tables/find-type-training";

    public static final String TRAINING_TABLE_USER = "/training-tables/users/{userId}";

    public static final String GYM_MACHINE_ID = "/training-tables/gym-machines/{idGymMachine}";

    public static final String GYM_MACHINE_EMPTY_REPOSITORY = "/training-tables/gym-machines/empty";

    public static final String TRAINING_ID = "/training-tables/trainings/{idTraining}";

    public static final String TRAINING_TABLE = "/training-table";

    public static final String GYM_MACHINE = "/training-tables/gym-machine";

    public static final String TRAINING = "/training-tables/training";

    public static final String WORKED_WEIGHTS = "/training-tables/worked-weights";

    public static final String GENERATE_TOKEN = "/api/gymfit/training-tables/generate-token";

    public static final String CURRENT_USER = "/current-user";

    public static final String NOT_FOUND_TRAINING_MSG = "No se encuentra el entrenamiento con ID: {}";

    public static final String UPDATE_TRAINING_MSG = "Entrenamiento con ID: {} actualizado con éxito.";

    public static final String NOT_FOUND_SYSTEM = " no se encuentra en el sistema.";

    public static <T> ResponseHTTP<T> createResponseHttp(HttpStatus httpStatus, T object, String message) {
        return new ResponseHTTP<>(httpStatus.value(), httpStatus.toString(), object, message);
    }

}
