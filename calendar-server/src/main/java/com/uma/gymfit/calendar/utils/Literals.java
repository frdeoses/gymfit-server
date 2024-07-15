package com.uma.gymfit.calendar.utils;

public class Literals {

    private Literals() {
        throw new UnsupportedOperationException("Esta es una clase de utilidad y no puede ser instanciada.");
    }

    public static final String API = "/api/gymfit";

    public static final String CALENDARS = "/calendars";

    public static final String CALENDARS_PUBLISHED = "/calendars/published";

    public static final String CALENDAR_ID = "/calendars/{idCalendar}";

    public static final String CALENDAR = "/calendar";

    public static final String COMMENT = "/calendar/comment";

    public static final String GENERATE_TOKEN = "/api/gymfit/calendars/generate-token";

    public static final String CURRENT_USER = "/current-user";

    public static final String ERROR_AL_GUARDAR_EL_EVENTO_EN_LA_BASE_DE_DATOS = "ERROR: Error al guardar el evento en la base de datos - {}";

    public static final String ERROR_AL_CREAR_EL_EVENTO_EN_LA_BASE_DE_DATOS = "Error al crear el evento en la base de datos.";
    
}
