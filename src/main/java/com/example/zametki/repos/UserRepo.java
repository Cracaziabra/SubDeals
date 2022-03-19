package com.example.zametki.repos;

import com.example.zametki.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepo extends CrudRepository<User, Long> {

    public User findByUsername(String username);

}
