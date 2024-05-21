package com.uma.gymfit.trainingtable.utils;

import lombok.extern.slf4j.Slf4j;

import static com.uma.gymfit.trainingtable.utils.Literals.NOT_FOUND_TRAINING_MSG;
import static com.uma.gymfit.trainingtable.utils.Literals.UPDATE_TRAINING_MSG;

@Slf4j
public class TrainingTrace {

    private TrainingTrace() {
        throw new UnsupportedOperationException("Esta es una clase de utilidad y no puede ser instanciada.");
    }

    public static void logErrorNotFoundTraining(String id) {
        log.error(NOT_FOUND_TRAINING_MSG, id);
    }

    public static void logUpdateTraining(String id) {
        log.error(UPDATE_TRAINING_MSG, id);
    }

}
