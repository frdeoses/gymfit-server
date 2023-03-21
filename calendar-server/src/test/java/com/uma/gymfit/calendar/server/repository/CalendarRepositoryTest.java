package com.uma.gymfit.calendar.server.repository;

import com.uma.gymfit.calendar.model.calendar.Calendar;
import com.uma.gymfit.calendar.repository.ICalendarRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.util.List;
import java.util.Optional;

@DataMongoTest
public class CalendarRepositoryTest {

    @Autowired
    private ICalendarRepository calendarRepository;

    private Calendar calendar;

    @BeforeEach
    void setup() {
        calendar = new Calendar();
        calendar.setTitle("Test1");
        calendar.setDescription("prueba 1");
        calendar.setPublished(false);
        calendar.setId("1");
    }

    @AfterEach
    void emptyRepository(){
        calendarRepository.deleteAll();
    }

    @DisplayName("Test para crear un evento.")
    @Test
    void crearEventoTest() {

        Calendar calendar1 = new Calendar();
        calendar1.setTitle("Test1");
        calendar1.setDescription("prueba 1");
        calendar1.setPublished(false);

        Calendar calendarSave = calendarRepository.save(calendar1);
        Assertions.assertThat(calendarSave).isNotNull();
        Assertions.assertThat(calendarSave.getTitle()).isEqualToIgnoringCase("Test1");

    }

    @DisplayName("Test para listar todos los eventos.")
    @Test
    void listarEventoTest() {

        // give
        Calendar calendar1 = new Calendar();
        calendar1.setTitle("Test1");
        calendar1.setDescription("prueba 1");
        calendar1.setPublished(false);

        calendarRepository.save(calendar1);
        calendarRepository.save(calendar);

        // when
        List<Calendar> calendarList = calendarRepository.findAll();

        // then
        Assertions.assertThat(calendarList).isNotNull();
        Assertions.assertThat(calendarList.size()).isEqualTo(2);

    }

    @DisplayName("Test para obtener un evento.")
    @Test
    void obtenerEventoTest() {

        // give
        calendarRepository.save(calendar);

        // when
        Calendar calendarFound = calendarRepository.findById("1").get();


        // then
        Assertions.assertThat(calendarFound).isNotNull();

    }

    @DisplayName("Test para editar un evento.")
    @Test
    void editarEventoTest() {

        // give
        calendarRepository.save(calendar);

        // when
        Calendar calendarSave = calendarRepository.findById(calendar.getId()).get();

        calendarSave.setTitle("Prueba editar");
        calendarSave.setPublished(true);

        Calendar calendarEdit = calendarRepository.save(calendarSave);


        // then

        Assertions.assertThat(calendarEdit.getTitle()).isEqualTo("Prueba editar");
        Assertions.assertThat(calendarEdit.isPublished()).isTrue();


    }

    @DisplayName("Test para eliminar un evento.")
    @Test
    void eliminarEventoTest() {

        // give
        calendarRepository.save(calendar);

        // when
        calendarRepository.deleteById(calendar.getId());

        Optional<Calendar> calendarOptional = calendarRepository.findById(calendar.getId());


        // then

        Assertions.assertThat(calendarOptional).isEmpty();

    }


}
