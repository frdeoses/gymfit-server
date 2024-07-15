package com.uma.gymfit.calendar.service.impl;

import com.uma.gymfit.calendar.exception.CalendarCreationException;
import com.uma.gymfit.calendar.exception.CalendarNotFoundException;
import com.uma.gymfit.calendar.model.calendar.Calendar;
import com.uma.gymfit.calendar.model.calendar.Comment;
import com.uma.gymfit.calendar.model.dto.CalendarDto;
import com.uma.gymfit.calendar.model.dto.CommentDto;
import com.uma.gymfit.calendar.repository.ICalendarRepository;
import com.uma.gymfit.calendar.service.ICalendarService;
import com.uma.gymfit.calendar.utils.Literals;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.uma.gymfit.calendar.utils.CalendarTrace.logErrorNotFoundCalendar;
import static com.uma.gymfit.calendar.utils.CalendarTrace.logInfoSaveCalendar;
import static com.uma.gymfit.calendar.utils.CalendarTrace.logInfoSaveOK;
import static com.uma.gymfit.calendar.utils.Literals.ERROR_AL_CREAR_EL_EVENTO_EN_LA_BASE_DE_DATOS;

@AllArgsConstructor
@Service
@Slf4j
public class CalendarService
        implements ICalendarService {

    private final ICalendarRepository calendarRepository;


    /**
     * Devuelve todos los calendarios almacenados en BB DD
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
     * @return
     * @
     */
    @Override
    public Calendar findCalendar(String idCalendar) {

        log.info("Buscamos el calendario en el sistema....");

        return calendarRepository.findById(idCalendar)
                .orElseThrow(() -> {
                    logErrorNotFoundCalendar(idCalendar);
                    return new CalendarNotFoundException("ERROR: Calendario no se encuentra en el sistema.");
                });

    }

    /**
     * Crea un calendario
     */
    @Override
    public void createCalendar(CalendarDto calendarDto) {

        //en caso de no tener problemas guardaremos en el repositorio.
        log.info("Verificamos que el evento que quiere crear no esta ya en el sistema...");

        try {

            Calendar calendar = Calendar.builder()
                    .description(calendarDto.getDescription())
                    .creationDate(LocalDateTime.now())
                    .lastUpdateDate(LocalDateTime.now())
                    .title(calendarDto.getTitle())
                    .published(calendarDto.isPublished())
                    .build();

            logInfoSaveCalendar(calendar);


            calendarRepository.save(calendar);

            logInfoSaveOK();
        } catch (DataAccessException e) {
            log.error(Literals.ERROR_AL_GUARDAR_EL_EVENTO_EN_LA_BASE_DE_DATOS, e.getMessage());
            throw new CalendarCreationException(ERROR_AL_CREAR_EL_EVENTO_EN_LA_BASE_DE_DATOS);
        }


    }

    /**
     * Borra un calendario por su id
     */
    @Override
    public void deleteCalendar(String id) {

        //comprobamos que el 'id' se encuentra en el repositorio
        log.info("Comprobamos en el sistema que existe el calendario en el sistema ");

        try {
            calendarRepository.deleteById(id);
            log.info("Tabla de entrenamiento con ID: {} eliminada con éxito.", id);
        } catch (EmptyResultDataAccessException e) {
            log.error("La tabla de entrenamiento con ID: {} no se encuentra en el sistema.Error: {}", id, e.getMessage());
            throw new CalendarNotFoundException("La tabla de entrenamiento con ID: " + id + " no se encuentra en el sistema.");
        }

    }


    /**
     * Modifica un calendario
     */
    @Override
    public CalendarDto updateCalendar(CalendarDto calendar) {


        // comprobamos que se encuentra en la BB DD
        log.info("Comprobamos en el sistema que existe el calendario.");

        Calendar calendarSave = calendarRepository.findById(calendar.getId())
                .orElseThrow(() -> {
                    log.error("No se encuentra el calendario que quieres modificar - ID: {}.", calendar.getId());
                    return new CalendarNotFoundException("No se encuentra el calendario que quieres modificar");
                });

        Calendar calendarUpdate = updateFields(calendar, calendarSave);

        try {
            calendarRepository.save(calendarUpdate);


        } catch (DataAccessException e) {
            log.error(Literals.ERROR_AL_GUARDAR_EL_EVENTO_EN_LA_BASE_DE_DATOS, e.getMessage());
            throw new CalendarCreationException(ERROR_AL_CREAR_EL_EVENTO_EN_LA_BASE_DE_DATOS);

        }

        return calendar;

    }

    private Calendar updateFields(CalendarDto calendar, Calendar calendarSave) {
        return calendarSave.toBuilder()
                .title(calendar.getTitle())
                .published(calendar.isPublished())
                .description(calendar.getDescription())
                .comments(calendar.getComments())
                .lastUpdateDate(LocalDateTime.now())
                .build();
    }

    @Override
    public List<Calendar> allCalendarsPublishedActive() {
        log.info("Buscamos en el sistema los eventos que estén publicados....");
        return calendarRepository.findByPublished(true);
    }

    @Override
    public Comment addComment(CommentDto commentDto) {

        Calendar calendar = calendarRepository.findById(commentDto.getIdCalendar())
                .orElseThrow(() -> {
                    logErrorNotFoundCalendar(commentDto.getIdCalendar());
                    return new CalendarNotFoundException("ERROR: Calendario no se encuentra en el sistema.");
                });

        Comment comment = getComment(commentDto);

        log.info("OK: Comentario creado con éxito. - Comentario: {}", comment);

        if (Objects.isNull(calendar.getComments()) || calendar.getComments().isEmpty())
            calendar.setComments(new ArrayList<>());

        calendar.getComments().add(comment);

        log.info("OK: Comentario añadido con éxito. - Comentario: {}", comment);

        try {
            logInfoSaveCalendar(calendar);

            calendarRepository.save(calendar);

            logInfoSaveOK();
        } catch (DataAccessException e) {
            log.error(Literals.ERROR_AL_GUARDAR_EL_EVENTO_EN_LA_BASE_DE_DATOS, e.getMessage());
            throw new CalendarCreationException(ERROR_AL_CREAR_EL_EVENTO_EN_LA_BASE_DE_DATOS);
        }

        return comment;
    }

    private static Comment getComment(CommentDto commentDto) {
        return Comment.builder()
                .date(LocalDateTime.now())
                .userName(commentDto.getUsername())
                .text(commentDto.getComment())
                .build();
    }

}
