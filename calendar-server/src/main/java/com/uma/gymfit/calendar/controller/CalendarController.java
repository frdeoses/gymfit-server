package com.uma.gymfit.calendar.controller;

import com.uma.gymfit.calendar.model.calendar.Calendar;
import com.uma.gymfit.calendar.model.calendar.ResponseHTTP;
import com.uma.gymfit.calendar.service.ICalendarService;
import com.uma.gymfit.calendar.utils.Literals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.DELETE, RequestMethod.POST, RequestMethod.PATCH})
@RequestMapping(Literals.API)
public class CalendarController {

    @Autowired
    private ICalendarService calendarService;

    @GetMapping(Literals.CALENDARS)
    public ResponseEntity<ResponseHTTP<List<Calendar>>> allCalendars() {

        return ResponseEntity.ok(createResponseHttp(HttpStatus.OK, calendarService.allCalendars(), null));
    }

    @GetMapping(Literals.CALENDARS_PUBLISHED)
    public ResponseEntity<ResponseHTTP<List<Calendar>>> allCalendarsPublished() {
        return ResponseEntity.ok(createResponseHttp(HttpStatus.OK, calendarService.allCalendarsPublishedActive(), null));
    }

    @GetMapping(Literals.CALENDAR_ID)
    public ResponseEntity<ResponseHTTP<?>> findCalendar(@PathVariable String idCalendar) {
        try {
            Calendar calendar = calendarService.findCalendar(idCalendar);
            return ResponseEntity.ok(createResponseHttp(HttpStatus.OK, calendar, null));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(createResponseHttp(HttpStatus.INTERNAL_SERVER_ERROR, idCalendar, e.getMessage()));
        }
    }

    @PostMapping(Literals.CALENDAR)
    public ResponseEntity<ResponseHTTP<Calendar>> createCalendar(@Validated @RequestBody Calendar calendar) {

        try {
            calendarService.createCalendar(calendar);
            return ResponseEntity.created(URI.create(Literals.CALENDAR)).body(createResponseHttp(HttpStatus.CREATED, calendar, null));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(createResponseHttp(HttpStatus.INTERNAL_SERVER_ERROR, calendar, e.getMessage()));
        }

    }


    @PatchMapping(Literals.CALENDAR)
    public ResponseEntity<ResponseHTTP<Calendar>> updateCalendar(@RequestBody Calendar calendar) {

        try {

            Calendar calendarSave = calendarService.updateCalendar(calendar);

            return ResponseEntity.ok(createResponseHttp(HttpStatus.OK, calendarSave, null));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(createResponseHttp(HttpStatus.INTERNAL_SERVER_ERROR, calendar, e.getMessage()));
        }

    }


    @DeleteMapping(Literals.CALENDAR_ID)
    public ResponseEntity<ResponseHTTP<String>> deleteCalendar(@PathVariable String idCalendar) {

        try {
            calendarService.deleteCalendar(idCalendar);
            return ResponseEntity.ok(createResponseHttp(HttpStatus.OK, idCalendar, null));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(createResponseHttp(HttpStatus.INTERNAL_SERVER_ERROR, idCalendar, e.getMessage()));
        }

    }

    private <T> ResponseHTTP<T> createResponseHttp(HttpStatus httpStatus, T object, String message) {
        return new ResponseHTTP<>(httpStatus.value(), httpStatus.toString(), object, message);
    }


}
