package com.upscale.registration.repositories;

import com.upscale.registration.model.Event;
import com.upscale.registration.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface EventsRepository extends CrudRepository<Event, Long> {
    public List<Event> findByIsPassed(Boolean IsPassed);
    public List<Event> findById(long id);
//    public List<Event> findEventsByUserId(long id)
//    public List<Event> findEventByUserName(String name);

}
