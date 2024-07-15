package com.uma.gymfit.calendar.repository;


import com.uma.gymfit.calendar.model.calendar.Calendar;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ICalendarRepository extends MongoRepository<Calendar,String> {

    List<Calendar> findByPublished(boolean published);
}
