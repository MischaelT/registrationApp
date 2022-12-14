package com.upscale.registration.repositories;


import com.upscale.registration.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UsersRepository extends CrudRepository<User, Long> {
}
