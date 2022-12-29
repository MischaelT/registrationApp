package com.upscale.registration.repositories;

import com.upscale.registration.model.Event;
import org.springframework.data.repository.CrudRepository;


import java.util.List;

public interface EventsRepository extends CrudRepository<Event, Long> {
     List<Event> findByIsPassed(Boolean IsPassed);
     List<Event> findById(long id);
     List<Event> findByLinkedInLink(String linkedInLink);


}
