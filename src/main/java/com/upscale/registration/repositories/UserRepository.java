package com.upscale.registration.repositories;

import com.upscale.registration.model.Event;
import com.upscale.registration.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {
    public List<User> findById(long id);
}
