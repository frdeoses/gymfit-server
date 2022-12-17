package com.uma.gymfit.calendar.service.impl;

import com.uma.gymfit.calendar.model.calendar.Calendar;
import com.uma.gymfit.calendar.repository.ICalendarRepository;
import com.uma.gymfit.calendar.service.ICalendarService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Service
@Slf4j
public class CalendarService
        implements ICalendarService {

    @Autowired
    private ICalendarRepository calendarRepository;

    /**
     * Devuelve todos los calendarios almacenados en BBDD
     *
     * @return List<Calendar>
     */
    @Override
    public List<Calendar> allCalendars() {
        return calendarRepository.findAll();
    }

    /**
     * Devuelve el calendarios almacenado en BBDD
     *
     * @param idCalendar
     * @return
     * @throws Exception
     */
    @Override
    public Calendar findCalendar(String idCalendar) throws Exception {

        log.info("Buscamos el calendario en el sistema....");
        if (calendarRepository.existsById(idCalendar)) {
            log.info("OK: Calendario encontrado.....");
            return calendarRepository.findById(idCalendar).get();
        }

        log.error("ERROR: Calendario no se encuentra en el sistema...");
        throw new Exception("ERROR: Calendario no se encuentra en el sistema...");
    }

    /**
     * Crea un calendario
     *
     * @param calendar
     */
    @Override
    public void createCalendar(Calendar calendar) throws Exception {

        //en caso de no tener problemas guardaremos en el repositorio.
        log.info("Procedemos a guardar en el sistema el siguiente calendario: {}.", calendar);
        calendar.setId(UUID.randomUUID().toString());
        calendarRepository.save(calendar);
        log.info("OK: Calendario guardado con exito.");

    }

    /**
     * Borra un calendario por su id
     *
     * @param id
     */
    @Override
    public void deleteCalendar(String id) throws Exception {

        //comprobamos que el id se encuentra en el reepositorio
        log.info("Comprobamos en el sistema que exite el calendario en el sistema ");
        if (calendarRepository.existsById(id)) {

            log.info("Exite el calendario en el sistema.");
            //una vez este todo correcto guardaremos el dato.
            calendarRepository.deleteById(id);
            log.info("OK: Calendario eliminado con exito.");
        } else {
            log.error("El calendario que quiere eliminar no se encuentra en el sistema");
            throw new Exception("El calendario que quiere eliminar no se encuentra en el sistema");
        }

    }


    /**
     * Modifica un calendario
     *
     * @param calendar
     */
    @Override
    public void updateCalendar(Calendar calendar) throws Exception {


        // comprobamos que se encuentra en la BBDD
        log.info("Comprobamos en el sistema que exite el calendario.");
        if (calendarRepository.existsById(calendar.getId())) {

            log.info("Exite el calendario en el sistema.");
            // insertamos nuevo
            calendarRepository.save(calendar);
            log.info("OK: Calendario guardado con exito.");

        } else {
            log.error("No se encuentra el calendario que quieres modificar");
            throw new Exception("No se encuentra el calendario que quieres modificar");
        }

    }


}
