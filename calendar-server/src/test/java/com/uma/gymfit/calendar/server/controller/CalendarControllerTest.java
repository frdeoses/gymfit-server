package com.uma.gymfit.calendar.server.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uma.gymfit.calendar.config.JwtAuthenticationEntryPoint;
import com.uma.gymfit.calendar.config.JwtAuthenticationFilter;
import com.uma.gymfit.calendar.config.JwtUtils;
import com.uma.gymfit.calendar.exception.CalendarNotFoundException;
import com.uma.gymfit.calendar.model.calendar.Calendar;
import com.uma.gymfit.calendar.model.calendar.ResponseHTTP;
import com.uma.gymfit.calendar.security.service.impl.UserDetailsServiceImpl;
import com.uma.gymfit.calendar.service.impl.CalendarService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.doNothing;
import static org.mockito.BDDMockito.doThrow;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@AutoConfigureMockMvc(addFilters = false)
class CalendarControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CalendarService calendarService;

    @MockBean
    private UserDetailsServiceImpl userDetailsService;

    @MockBean
    private JwtUtils jwtUtils;

    @MockBean
    private JwtAuthenticationEntryPoint unauthorizeHandler;
    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private ObjectMapper objectMapper;

    private Calendar calendar;

    @BeforeEach
    void setup() {
        calendar = Calendar.builder()
                .id("1")
                .title("Test1")
                .description("prueba 1")
                .published(false)
                .build();
    }

    @DisplayName("Test para crear un evento:")
    @Test
    void createCalendarTest() throws Exception {

        // given


        ResponseHTTP<Calendar> responseHTTP = createResponseHttp(HttpStatus.CREATED, calendar, null);

        doNothing().when(calendarService).createCalendar(calendar);

        ResultActions response = mockMvc.perform(post("/api/gymfit/calendar").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(calendar
        )));

        response.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.body.id", is(responseHTTP.getBody().getId())))
                .andDo(print());

    }

    @DisplayName("Test para ver todos los eventos:")
    @Test
    void allCalendarTest() throws Exception {

        // given

        List<Calendar> calendarList = new ArrayList<>();

        Calendar calendar1 = calendar.toBuilder()
                .id("1")
                .title("Test1")
                .description("prueba 1")
                .published(false)
                .build();

        calendarList.add(calendar1);

        Calendar calendar2 = calendar.toBuilder()
                .id("2")
                .title("Test2")
                .description("prueba 2")
                .published(false)
                .build();

        calendarList.add(calendar2);

        Calendar calendar3 = calendar.toBuilder()
                .id("3")
                .title("Test3")
                .description("prueba 3")
                .published(false)
                .build();

        calendarList.add(calendar3);

        given(calendarService.allCalendars()).willReturn(calendarList);

        // when


        ResultActions response = mockMvc.perform(get("/api/gymfit/calendars"));

        // then

        response.andExpect(status().isOk()).andDo(print())
                .andExpect(jsonPath("$.body.size()", is(calendarList.size())));

    }

    @DisplayName("Test para obtener un eventos:")
    @Test
    void foundCalendarTest() throws Exception {

        // given


        given(calendarService.findCalendar("1")).willReturn(calendar);
        ResponseHTTP<Calendar> responseHTTP = createResponseHttp(HttpStatus.OK, calendar, null);

        // when


        ResultActions response = mockMvc.perform(get("/api/gymfit/calendars/{idCalendar}", "1"));

        // then

        response.andExpect(status().isOk()).andDo(print())
                .andExpect(jsonPath("$.body.id", is(calendar.getId())))
                .andExpect(jsonPath("$.body.title", is(calendar.getTitle())))
                .andExpect(jsonPath("$.body.description", is(calendar.getDescription())))
                .andExpect(jsonPath("$.body.published", is(calendar.isPublished())))
                // Agrega comparaciones para otras propiedades si es necesario
                .andExpect(jsonPath("$.error").doesNotExist());

    }

    @DisplayName("Test de error que no encuentra eventos:")
    @Test
    void notFoundCalendarTest() throws Exception {

        // given


        CalendarNotFoundException e = new CalendarNotFoundException("ERROR: Calendario no se encuentra en el sistema.");

        doThrow(e).when(calendarService).findCalendar("2");

        // when
        ResultActions response = mockMvc.perform(get("/api/gymfit/calendars/{idCalendar}", "2"));

        // then
        response.andExpect(status().isInternalServerError()).andDo(print());

    }

    @DisplayName("Test para editar un eventos:")
    @Test
    void editCalendarTest() throws Exception {

        // given

        Calendar calendar = Calendar.builder()
                .id("1")
                .title("Test1")
                .description("prueba 1")
                .published(false)
                .comments(new ArrayList<>())
                .build();

        Calendar calendarEdit = calendar.toBuilder()
                .id("2")
                .title("Test2")
                .description("prueba 2")
                .published(true)
                .build();

        ResponseHTTP<Calendar> responseHTTP = createResponseHttp(HttpStatus.OK, calendarEdit, null);
        
        given(calendarService.updateCalendar(calendar)).willReturn(calendarEdit);

        // when
        ResultActions response = mockMvc.perform(patch("/api/gymfit/calendar")
                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(calendar)));

        // then
        response.andExpect(status().isOk()).andDo(print())
                .andExpect(jsonPath("$.body.id", is(responseHTTP.getBody().getId())));

    }

    @DisplayName("Test para eliminar un eventos:")
    @Test
    void deleteCalendarTest() throws Exception {

        // given


        willDoNothing().given(calendarService).deleteCalendar(calendar.getId());

        // when
        ResultActions response = mockMvc.perform(delete("/api/gymfit/calendars/{idCalendar}", calendar.getId()));

        // then
        response.andExpect(status().isOk()).andDo(print());

    }

    @DisplayName("Test para editar un eventos con errores:")
    @Test
    void editCalendarErrorTest() throws Exception {

        // given


        Calendar calendarNotFound = calendar.toBuilder()
                .id("3").build();


        doThrow(CalendarNotFoundException.class).when(calendarService).updateCalendar(calendarNotFound);

        ResponseHTTP<Calendar> responseHTTP = createResponseHttp(HttpStatus.INTERNAL_SERVER_ERROR, calendarNotFound, "");


        // when
        ResultActions response = mockMvc.perform(patch("/api/gymfit/calendar").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(calendarNotFound)));

        // then
        response.andExpect(status().isInternalServerError()).andDo(print())
                .andExpect(jsonPath("$.body.id", is(responseHTTP.getBody().getId())));

    }

    private <T> ResponseHTTP<T> createResponseHttp(HttpStatus httpStatus, T object, String message) {
        return new ResponseHTTP<>(httpStatus.value(), httpStatus.toString(), object, message);
    }

}
