package com.uma.gymfit.calendar.server.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uma.gymfit.calendar.config.JwtAuthenticationEntryPoint;
import com.uma.gymfit.calendar.config.JwtAuthenticationFilter;
import com.uma.gymfit.calendar.config.JwtUtils;
import com.uma.gymfit.calendar.exception.CalendarNotFoundException;
import com.uma.gymfit.calendar.model.calendar.Calendar;
import com.uma.gymfit.calendar.security.service.impl.UserDetailsServiceImpl;
import com.uma.gymfit.calendar.service.impl.CalendarService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

    @DisplayName("Test para crear un evento:")
    @Test
    void createCalendarTest() throws Exception {

        // given
        Calendar calendar = new Calendar();
        calendar.setTitle("Test1");
        calendar.setDescription("prueba 1");
        calendar.setPublished(false);
        calendar.setId("1");
        calendar.setComments(new ArrayList<>());

        doNothing().when(calendarService).createCalendar(calendar);

        ResultActions response = mockMvc.perform(post("/api/gymfit/calendar").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(calendar)));

        response.andDo(print()).andExpect(status().isCreated()).andDo(print());

    }

    @DisplayName("Test para ver todos los eventos:")
    @Test
    void allCalendarTest() throws Exception {

        // given

        List<Calendar> calendarList = new ArrayList<>();

        Calendar calendar = new Calendar();
        calendar.setTitle("Test1");
        calendar.setDescription("prueba 1");
        calendar.setPublished(false);
        calendar.setId("1");
        calendar.setComments(new ArrayList<>());
        calendarList.add(calendar);

        Calendar calendar2 = new Calendar();
        calendar2.setTitle("Test2");
        calendar2.setDescription("prueba 2");
        calendar2.setPublished(false);
        calendar2.setId("2");
        calendar.setComments(new ArrayList<>());
        calendarList.add(calendar2);

        Calendar calendar3 = new Calendar();
        calendar3.setTitle("Test3");
        calendar3.setDescription("prueba 3");
        calendar3.setPublished(false);
        calendar3.setId("3");
        calendar3.setComments(new ArrayList<>());
        calendarList.add(calendar3);

        given(calendarService.allCalendars()).willReturn(calendarList);

        // when


        ResultActions response = mockMvc.perform(get("/api/gymfit/calendars"));

        // then

        response.andExpect(status().isOk()).andDo(print()).andExpect(jsonPath("$.size()", is(calendarList.size())));

    }

    @DisplayName("Test para obtener un eventos:")
    @Test
    void foundCalendarTest() throws Exception {

        // given


        Calendar calendar = new Calendar();
        calendar.setTitle("Test1");
        calendar.setDescription("prueba 1");
        calendar.setPublished(false);
        calendar.setId("1");
        calendar.setComments(new ArrayList<>());


        given(calendarService.findCalendar("1")).willReturn(calendar);

        // when


        ResultActions response = mockMvc.perform(get("/api/gymfit/calendars/{idCalendar}", "1"));

        // then

        response.andExpect(status().isOk()).andDo(print()).andExpect(jsonPath("$.title", is(calendar.getTitle()))).andExpect(jsonPath("$.published", is(calendar.isPublished()))).andExpect(jsonPath("$.description", is(calendar.getDescription())));

    }

    @DisplayName("Test de error que no encuentra eventos:")
    @Test
    void notFoundCalendarTest() throws Exception {

        // given

        Calendar calendar = new Calendar();
        calendar.setTitle("Test1");
        calendar.setDescription("prueba 1");
        calendar.setPublished(false);
        calendar.setId("1");
        calendar.setComments(new ArrayList<>());

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


        Calendar calendar = new Calendar();
        calendar.setTitle("Test1");
        calendar.setDescription("prueba 1");
        calendar.setPublished(false);
        calendar.setId("1");
        calendar.setComments(new ArrayList<>());

        Calendar calendarEdit = new Calendar();
        calendarEdit.setTitle("Test2");
        calendarEdit.setDescription("prueba 2");
        calendarEdit.setPublished(true);
        calendarEdit.setId("2");
        calendarEdit.setComments(new ArrayList<>());

        given(calendarService.updateCalendar(calendar)).willReturn(calendar);

        given(calendarService.updateCalendar(calendarEdit)).willReturn(calendarEdit);


        // when
        ResultActions response = mockMvc.perform(patch("/api/gymfit/calendar").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(calendarEdit)));

        // then
        response.andExpect(status().isOk()).andDo(print()).andExpect(jsonPath("$.id", is(calendarEdit.getId())));

    }

    @DisplayName("Test para eliminar un eventos:")
    @Test
    void deleteCalendarTest() throws Exception {

        // given
        Calendar calendar = new Calendar();
        calendar.setTitle("Test1");
        calendar.setDescription("prueba 1");
        calendar.setPublished(false);
        calendar.setId("1");
        calendar.setComments(new ArrayList<>());

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


        Calendar calendar = new Calendar();
        calendar.setTitle("Test1");
        calendar.setDescription("prueba 1");
        calendar.setPublished(false);
        calendar.setId("1");
        calendar.setComments(new ArrayList<>());


        doThrow(CalendarNotFoundException.class).when(calendarService).updateCalendar(calendar);


        // when
        ResultActions response = mockMvc.perform(patch("/api/gymfit/calendar").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(calendar)));

        // then
        response.andExpect(status().isInternalServerError()).andDo(print()).andExpect(jsonPath("$.title", is(calendar.getTitle())));

    }

}
