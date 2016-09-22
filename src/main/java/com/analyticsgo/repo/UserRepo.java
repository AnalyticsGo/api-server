package com.analyticsgo.repo;

import com.analyticsgo.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepo extends CrudRepository<User, Long> {

  User findByUsername(String username);

}
