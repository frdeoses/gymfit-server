package com.uma.gymfit.calendar.controller;

import com.uma.gymfit.calendar.model.calendar.Calendar;
import com.uma.gymfit.calendar.model.calendar.Comment;
import com.uma.gymfit.calendar.model.calendar.ResponseHTTP;
import com.uma.gymfit.calendar.model.dto.CalendarDto;
import com.uma.gymfit.calendar.model.dto.CommentDto;
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

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.DELETE, RequestMethod.POST, RequestMethod.PATCH})
@RequestMapping(Literals.API)
public class CalendarController {

    private final ICalendarService calendarService;

    @Autowired
    public CalendarController(ICalendarService calendarService) {
        this.calendarService = calendarService;
    }

    @GetMapping(Literals.CALENDARS)
    public ResponseEntity<ResponseHTTP<List<Calendar>>> allCalendars() {

        return ResponseEntity.ok(createResponseHttp(HttpStatus.OK, calendarService.allCalendars(), null));
    }

    @GetMapping(Literals.CALENDARS_PUBLISHED)
    public ResponseEntity<ResponseHTTP<List<Calendar>>> allCalendarsPublished() {
        return ResponseEntity.ok(createResponseHttp(HttpStatus.OK, calendarService.allCalendarsPublishedActive(), null));
    }

    @GetMapping(Literals.CALENDAR_ID)
    public ResponseEntity<ResponseHTTP<Calendar>> findCalendar(@PathVariable String idCalendar) {
        try {

            Calendar calendar = calendarService.findCalendar(idCalendar);
            return ResponseEntity.ok(createResponseHttp(HttpStatus.OK, calendar, null));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(createResponseHttp(HttpStatus.INTERNAL_SERVER_ERROR, null, e.getMessage()));
        }
    }

    @PostMapping(Literals.CALENDAR)
    public ResponseEntity<ResponseHTTP<CalendarDto>> createCalendar(@Validated @RequestBody CalendarDto calendar) {

        try {
            calendarService.createCalendar(calendar);
            return ResponseEntity.created(URI.create(Literals.CALENDAR)).body(createResponseHttp(HttpStatus.CREATED, calendar, null));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(createResponseHttp(HttpStatus.INTERNAL_SERVER_ERROR, calendar, e.getMessage()));
        }

    }


    @PatchMapping(Literals.CALENDAR)
    public ResponseEntity<ResponseHTTP<CalendarDto>> updateCalendar(@RequestBody CalendarDto calendarDto) {

        try {

            calendarService.updateCalendar(calendarDto);

            return ResponseEntity.ok(createResponseHttp(HttpStatus.OK, calendarDto, null));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(createResponseHttp(HttpStatus.INTERNAL_SERVER_ERROR, calendarDto, e.getMessage()));
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

    @PostMapping(Literals.COMMENT)
    public ResponseEntity<ResponseHTTP<Comment>> createComment(@Validated @RequestBody @Valid CommentDto comment) {

        try {
            return ResponseEntity.created(URI.create(Literals.CALENDAR)).body(createResponseHttp(HttpStatus.CREATED, calendarService.addComment(comment), null));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(createResponseHttp(HttpStatus.INTERNAL_SERVER_ERROR, null, e.getMessage()));
        }

    }

    private <T> ResponseHTTP<T> createResponseHttp(HttpStatus httpStatus, T object, String message) {
        return new ResponseHTTP<>(httpStatus.value(), httpStatus.toString(), object, message);
    }


}
