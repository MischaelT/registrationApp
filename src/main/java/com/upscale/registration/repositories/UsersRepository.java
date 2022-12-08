package com.upscale.registration.repositories;

import com.upscale.registration.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UsersRepository extends CrudRepository<User, Long> {
     List<User> findById(long id);
     List<User> findByNameAndLinkedInLink(String name, String linkedInLink);
}
