package com.uma.gymfit.calendar.service;

import com.uma.gymfit.calendar.model.Calendar;

import java.util.List;

public interface ICalendarService {

    /**
     * Devuelve todos los calendarios almacenados en BBDD
     *
     * @return List<U>
     */
    public List<Calendar> allCalendars();

    /**
     * Devuelve el calendarios almacenado en BBDD
     *
     * @param idCalendar
     * @return User
     */
    public Calendar findCalendar(String idCalendar) throws Exception;


    /**
     * Crea un calendario
     *
     * @param calendar
     */
    public void createCalendar(Calendar calendar) throws Exception;


    /**
     * Borra un calendario por su id
     *
     * @param id
     */
    public void deleteCalendar(String id) throws Exception;


    /**
     * Modifica un calendario
     *
     * @param calendar
     */
    public void updateCalendar(Calendar calendar) throws Exception;

}
