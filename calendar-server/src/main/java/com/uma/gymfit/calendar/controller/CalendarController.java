package com.uma.gymfit.calendar.controller;

import com.uma.gymfit.calendar.model.Calendar;
import com.uma.gymfit.calendar.model.ResponseHTTP;
import com.uma.gymfit.calendar.service.ICalendarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.DELETE, RequestMethod.POST, RequestMethod.PUT})
@RequestMapping("/api/gymfit")
public class CalendarController {

    @Autowired
    private ICalendarService calendarService;

    @GetMapping("/calendars")
    public ResponseEntity<List<Calendar>> allCalendars() {
        List<Calendar> allCalendar = new ArrayList<>();
        allCalendar = calendarService.allCalendars();
        return new ResponseEntity<>(allCalendar, HttpStatus.OK);
    }

    @GetMapping("/calendar/{idCalendar}")
    public ResponseEntity<Calendar> findCalendar(@PathVariable String idCalendar) {
        Calendar calendar;
        try {
            calendar = calendarService.findCalendar(idCalendar);
            ResponseHTTP res = new ResponseHTTP(HttpStatus.OK.value(), HttpStatus.OK.toString(), calendar, null);
            return new ResponseEntity(res, HttpStatus.OK);
        } catch (Exception e) {
            ResponseHTTP res = new ResponseHTTP(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), idCalendar, e.getMessage());
            return new ResponseEntity(res, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/calendar")
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


    @PutMapping("/calendar")
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


    @DeleteMapping("/calendar/{id}")
    public ResponseEntity<ResponseHTTP> deleteCalendar(@PathVariable String id) {

        try {
            calendarService.deleteCalendar(id);
            ResponseHTTP res = new ResponseHTTP(HttpStatus.OK.value(), HttpStatus.OK.toString(), id, null);
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (Exception e) {
            ResponseHTTP res = new ResponseHTTP(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), id, e.getMessage());
            return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


}
