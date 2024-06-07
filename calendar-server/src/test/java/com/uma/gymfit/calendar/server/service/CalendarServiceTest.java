package com.uma.gymfit.calendar.server.service;

import com.uma.gymfit.calendar.model.calendar.Calendar;
import com.uma.gymfit.calendar.model.dto.CalendarDto;
import com.uma.gymfit.calendar.repository.ICalendarRepository;
import com.uma.gymfit.calendar.service.impl.CalendarService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class CalendarServiceTest {

    @Mock
    ICalendarRepository calendarRepository;

    @InjectMocks
    private CalendarService calendarService;

    private Calendar calendar;

    private CalendarDto calendarDto;

    @BeforeEach
    void setup() {
        calendar = Calendar.builder()
                .id("1")
                .title("Test1")
                .description("prueba 1")
                .published(false)
                .build();


        calendarDto = CalendarDto.builder()
                .id("1")
                .title("Test1")
                .description("prueba 1")
                .published(false)
                .build();
    }

    @AfterEach
    void emptyRepository() {
        calendarRepository.deleteAll();
    }


    @DisplayName("Test ver todos los evento.")
    @Test
    void allCalendarsTest() {

        // given

        Calendar calendar1 = calendar.toBuilder()
                .title("Test2")
                .description("prueba 2")
                .published(false)
                .build();

        given(calendarRepository.findAll()).willReturn(List.of(calendar, calendar1));

        // when

        List<Calendar> calendarList = calendarService.allCalendars();

        // then
        assertThat(calendarList).hasSize(2);

    }

    @DisplayName("Test ver un listado vacio.")
    @Test
    void allCalendarsEmptyTest() {

        // given


        given(calendarRepository.findAll()).willReturn(Collections.emptyList());

        // when

        List<Calendar> calendarList = calendarService.allCalendars();

        // then

        assertThat(calendarList).isEmpty();

    }

    @DisplayName("Test para obtener un evento.")
    @Test
    void findCalendarTest() {

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
    void editCalendarTest() {

        // given

        calendar.setTitle("Prueba 2");
        calendar.setPublished(true);

        given(calendarRepository.existsById("1")).willReturn(true);

        given(calendarRepository.findById("1")).willReturn(Optional.ofNullable(calendar));

        // when


        calendarService.updateCalendar(calendarDto);

        Calendar calendarUpdate = calendarService.findCalendar(calendar.getId());


        // then

        assertThat(calendarUpdate).isNotNull();
        assertThat(calendarUpdate.getTitle()).isEqualTo("Prueba 2");

    }

    @DisplayName("Test para crear un evento.")
    @Test
    void createCalendarsTest() {


        given(calendarRepository.findById(calendar.getId())).willReturn(Optional.empty());


        given(calendarRepository.save(calendar)).willReturn(calendar);

        calendarService.createCalendar(calendarDto);

        assertThat(calendarRepository.findById(calendar.getId())).isNotNull();


    }

//    @DisplayName("Test para generar un error al crear un evento y este este creado.")
//    @Test
//    void createCalendarsErrorTest() throws Exception {
//
//
//        given(calendarRepository.findById(calendar.getId())).willReturn(Optional.of(calendar));
//
//        // FIXME: Revisar la validacion de que ya exista en el sistema
//        assertThrows(CalendarCreationException.class, () -> {
//            calendarService.createCalendar(calendar);
//        });
//
//        verify(calendarRepository, never()).save(any(Calendar.class));
//
//
//    }

    @DisplayName("Test para eliminar un evento.")
    @Test
    void deleteCalendarTest() {

        // given
        given(calendarRepository.existsById("1")).willReturn(true);
        willDoNothing().given(calendarRepository).deleteById("1");

        // when

        calendarService.deleteCalendar("1");

        // then

        verify(calendarRepository, times(1)).deleteById("1");

    }

}
