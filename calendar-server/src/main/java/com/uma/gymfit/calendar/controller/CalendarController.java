package com.uma.gymfit.calendar.controller;

import com.uma.gymfit.calendar.model.calendar.Calendar;
import com.uma.gymfit.calendar.model.calendar.ResponseHTTP;
import com.uma.gymfit.calendar.service.ICalendarService;
import com.uma.gymfit.calendar.utils.Literals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.DELETE, RequestMethod.POST, RequestMethod.PUT})
@RequestMapping(Literals.API)
public class CalendarController {

    @Autowired
    private ICalendarService calendarService;

    @GetMapping(Literals.CALENDARS)
    public ResponseEntity<List<Calendar>> allCalendars() {
        List<Calendar> allCalendar = new ArrayList<>();
        allCalendar = calendarService.allCalendars();
        return new ResponseEntity<>(allCalendar, HttpStatus.OK);
    }

    @GetMapping(Literals.CALENDAR_ID)
    public ResponseEntity<Calendar> findCalendar(@PathVariable String idCalendar) {
        Calendar calendar;
        try {
            calendar = calendarService.findCalendar(idCalendar);
            return new ResponseEntity(calendar, HttpStatus.OK);
        } catch (Exception e) {
            ResponseHTTP res = new ResponseHTTP(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), idCalendar, e.getMessage());
            return new ResponseEntity(res, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(Literals.CALENDAR)
    public ResponseEntity<ResponseHTTP> createCalendar(@Validated @RequestBody Calendar calendar) {

        try {
            calendarService.createCalendar(calendar);
            ResponseHTTP res = new ResponseHTTP(HttpStatus.CREATED.value(), HttpStatus.CREATED.toString(), calendar, null);
            return new ResponseEntity<>(res, HttpStatus.CREATED);
        } catch (Exception e) {
            ResponseHTTP res = new ResponseHTTP(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), calendar, e.getMessage());
            return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    @PutMapping(Literals.CALENDAR)
    public ResponseEntity<ResponseHTTP> updateCalendar(@RequestBody Calendar calendar) {

        try {
            calendarService.updateCalendar(calendar);
            ResponseHTTP res = new ResponseHTTP(HttpStatus.OK.value(), HttpStatus.OK.toString(), calendar, null);
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (Exception e) {
            ResponseHTTP res = new ResponseHTTP(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), calendar, e.getMessage());
            return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    @DeleteMapping(Literals.CALENDAR_ID)
    public ResponseEntity<ResponseHTTP> deleteCalendar(@PathVariable String idCalendar) {

        try {
            calendarService.deleteCalendar(idCalendar);
            ResponseHTTP res = new ResponseHTTP(HttpStatus.OK.value(), HttpStatus.OK.toString(), idCalendar, null);
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (Exception e) {
            ResponseHTTP res = new ResponseHTTP(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), idCalendar, e.getMessage());
            return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


}
