package com.uma.gymfit.calendar.repository;


import com.uma.gymfit.calendar.model.Calendar;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ICalendarRepository extends MongoRepository<Calendar,String> {
}
