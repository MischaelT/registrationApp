package com.upscale.registration.repositories;

import com.upscale.registration.model.Attendee;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AttendeeRepository extends CrudRepository<Attendee, Long> {
     List<Attendee> findById(long id);
     List<Attendee> findByNameAndLinkedInLink(String name, String linkedInLink);
}
