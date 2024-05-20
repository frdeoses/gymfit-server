package com.uma.gymfit.calendar.utils;

import com.uma.gymfit.calendar.model.calendar.Calendar;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CalendarTrace {

    private CalendarTrace() {
        throw new UnsupportedOperationException("Esta es una clase de utilidad y no puede ser instanciada.");
    }

    public static void logErrorNotFoundCalendar(String idCalendar) {
        log.error("ERROR: Calendario no se encuentra en el sistema - ID: {}.", idCalendar);
    }

    public static void logInfoSaveOK() {
        log.info("OK: Calendario guardado con éxito.");
    }

    public static void logInfoSaveCalendar(Calendar calendar) {
        log.info("Procedemos a guardar en el sistema el siguiente calendario: {}.", calendar);
    }

}
