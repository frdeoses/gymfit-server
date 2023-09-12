package com.uma.gymfit.calendar.service.impl;

import com.uma.gymfit.calendar.exception.CalendarCreationException;
import com.uma.gymfit.calendar.exception.CalendarNotFoundException;
import com.uma.gymfit.calendar.model.calendar.Calendar;
import com.uma.gymfit.calendar.repository.ICalendarRepository;
import com.uma.gymfit.calendar.service.ICalendarService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
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
     * Devuelve el calendario almacenado en BB DD
     *
     * @param idCalendar
     * @return
     * @
     */
    @Override
    public Calendar findCalendar(String idCalendar) {

        log.info("Buscamos el calendario en el sistema....");
        if (calendarRepository.existsById(idCalendar)) {
            log.info("OK: Calendario encontrado.....");
            return calendarRepository.findById(idCalendar).get();
        }

        log.error("ERROR: Calendario no se encuentra en el sistema - ID: {}.", idCalendar);
        throw new CalendarNotFoundException("ERROR: Calendario no se encuentra en el sistema.");
    }

    /**
     * Crea un calendario
     *
     * @param calendar
     */
    @Override
    public void createCalendar(Calendar calendar) {

        //en caso de no tener problemas guardaremos en el repositorio.
        log.info("Verificamos que el evento que quiere crear no esta ya en el sistema...");

        try {
            log.info("Procedemos a guardar en el sistema el siguiente calendario: {}.", calendar);
            calendar.setId(UUID.randomUUID().toString());
            calendar.setCreationDate(LocalDateTime.now());
            calendar.setLastUpdateDate(LocalDateTime.now());
            calendarRepository.save(calendar);
            log.info("OK: Calendario guardado con éxito.");
        } catch (DataAccessException e) {
            log.error("ERROR: Error al guardar el evento en la base de datos - {}", e.getMessage());
            throw new CalendarCreationException("Error al crear el evento en la base de datos.");
        }


    }

    /**
     * Borra un calendario por su id
     *
     * @param id
     */
    @Override
    public void deleteCalendar(String id) {

        //comprobamos que el 'id' se encuentra en el reepositorio
        log.info("Comprobamos en el sistema que existe el calendario en el sistema ");
        if (calendarRepository.existsById(id)) {

            log.info("Existe el calendario en el sistema.");
            //una vez este correcto guardaremos el dato.
            calendarRepository.deleteById(id);
            log.info("OK: Calendario eliminado con exito.");
        } else {
            log.error("El calendario que quiere eliminar no se encuentra en el sistema - ID: {}.", id);
            throw new CalendarNotFoundException("El calendario que quiere eliminar no se encuentra en el sistema");
        }

    }


    /**
     * Modifica un calendario
     *
     * @param calendar
     */
    @Override
    public Calendar updateCalendar(Calendar calendar) {


        // comprobamos que se encuentra en la BBDD
        log.info("Comprobamos en el sistema que existe el calendario.");
        if (calendarRepository.existsById(calendar.getId())) {

            log.info("Existe el calendario en el sistema.");
            calendar.setLastUpdateDate(LocalDateTime.now());

            // insertamos nuevo
            log.info("OK: Calendario guardado con éxito.");
            return calendarRepository.save(calendar);

        } else {
            log.error("No se encuentra el calendario que quieres modificar - ID: {}.", calendar.getId());
            throw new CalendarNotFoundException("No se encuentra el calendario que quieres modificar");
        }

    }

    @Override
    public List<Calendar> allCalendarsPublishedActive() {
        log.info("Buscamos en el sistema los eventos que estén publicados....");
        return calendarRepository.findByPublished(true);
    }


}
