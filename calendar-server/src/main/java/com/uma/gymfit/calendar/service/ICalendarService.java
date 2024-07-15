package com.uma.gymfit.calendar.service;

import com.uma.gymfit.calendar.model.calendar.Calendar;
import com.uma.gymfit.calendar.model.calendar.Comment;
import com.uma.gymfit.calendar.model.dto.CalendarDto;
import com.uma.gymfit.calendar.model.dto.CommentDto;

import java.util.List;

public interface ICalendarService {

    /**
     * Devuelve todos los calendarios almacenados en BBDD
     *
     * @return List<U>
     */
    List<Calendar> allCalendars();

    /**
     * Devuelve el calendarios almacenado en BBDD
     *
     * @param idCalendar
     * @return User
     */
    Calendar findCalendar(String idCalendar);


    /**
     * Crea un calendario
     *
     * @param calendar
     */
    void createCalendar(CalendarDto calendar);


    /**
     * Borra un calendario por su id
     *
     * @param id
     */
    void deleteCalendar(String id);


    /**
     * Modifica un calendario
     *
     * @param calendar
     */
    CalendarDto updateCalendar(CalendarDto calendar);


    /**
     * Devuelve todos los calendarios almacenados en BBDD que estan publicados
     *
     * @return List<U>
     */
    List<Calendar> allCalendarsPublishedActive();

    /**
     * Añadir un comentario
     */
    Comment addComment(CommentDto comment);

}
