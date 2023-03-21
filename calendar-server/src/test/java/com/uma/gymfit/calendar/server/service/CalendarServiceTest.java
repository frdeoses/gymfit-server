package com.uma.gymfit.calendar.server.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

import com.uma.gymfit.calendar.model.calendar.Calendar;
import com.uma.gymfit.calendar.repository.ICalendarRepository;
import com.uma.gymfit.calendar.service.impl.CalendarService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class CalendarServiceTest {

    @Mock
    ICalendarRepository calendarRepository;

    @InjectMocks
    private CalendarService calendarService;

    private Calendar calendar;

    @BeforeEach
    void setup() {
        calendar = new Calendar();
        calendar.setTitle("Test1");
        calendar.setDescription("prueba 1");
        calendar.setPublished(false);
        calendar.setId("1");
    }

//    @AfterEach
//    void emptyRepository(){
//        calendarRepository.deleteAll();
//    }


    @DisplayName("Test ver todos los evento.")
    @Test
    public void allCalendarsTest() {

        // given

        Calendar calendar1 = new Calendar();
        calendar1.setId("2");
        calendar1.setTitle("Prueba 2");
        calendar1.setPublished(true);

        given(calendarRepository.findAll()).willReturn(List.of(calendar, calendar1));

        // when

        List<Calendar> calendarList = calendarService.allCalendars();

        // then

        assertThat(calendarList.size()).isEqualTo(2);

    }

    @DisplayName("Test ver un listado vacio.")
    @Test
    public void allCalendarsEmptyTest() {

        // given

        Calendar calendar1 = new Calendar();
        calendar1.setId("2");
        calendar1.setTitle("Prueba 2");
        calendar1.setPublished(true);

        given(calendarRepository.findAll()).willReturn(Collections.emptyList());

        // when

        List<Calendar> calendarList = calendarService.allCalendars();

        // then

        assertThat(calendarList).isEmpty();
        assertThat(calendarList.size()).isEqualTo(0);

    }

    @DisplayName("Test para obtener un evento.")
    @Test
    public void findCalendarTest() throws Exception {

        // given

        given(calendarRepository.existsById("1")).willReturn(true);

        given(calendarRepository.findById("1")).willReturn(Optional.ofNullable(calendar));

        // when

        Calendar calendarSave = calendarService.findCalendar(calendar.getId());

        // then

        assertThat(calendarSave).isNotNull();

    }

    @DisplayName("Test para editar un evento.")
    @Test
    public void editCalendarTest() throws Exception {

        // given

        calendar.setTitle("Prueba 2");
        calendar.setPublished(true);

        given(calendarRepository.existsById("1")).willReturn(true);

        given(calendarRepository.findById("1")).willReturn(Optional.ofNullable(calendar));

        // when


        calendarService.updateCalendar(calendar);

        Calendar calendarUpdate = calendarService.findCalendar(calendar.getId());


        // then

        assertThat(calendarUpdate).isNotNull();
        assertThat(calendarUpdate.getTitle()).isEqualTo("Prueba 2");

    }

    @DisplayName("Test para crear un evento.")
    @Test
    void createCalendarsTest() throws Exception {


        given(calendarRepository.findById(calendar.getId())).willReturn(Optional.empty());


        given(calendarRepository.save(calendar)).willReturn(calendar);

        calendarService.createCalendar(calendar);

        assertThat(calendarRepository.findById(calendar.getId())).isNotNull();


    }

    @DisplayName("Test para generar un error al crear un evento.")
    @Test
    void createCalendarsErrorTest() throws Exception {


        given(calendarRepository.findById(calendar.getId())).willReturn(Optional.of(calendar));

        // FIXME: Revisar la validacion de que ya exista en el sistema
        assertThrows(Exception.class, () -> {
            calendarService.createCalendar(calendar);
        });

        verify(calendarRepository, never()).save(any(Calendar.class));


    }

    @DisplayName("Test para eliminar un evento.")
    @Test
    void deleteCalendarTest() throws Exception {

        // given
        given(calendarRepository.existsById("1")).willReturn(true);
        willDoNothing().given(calendarRepository).deleteById("1");

        // when

        calendarService.deleteCalendar("1");

        // then

        verify(calendarRepository, times(1)).deleteById("1");

    }

}
