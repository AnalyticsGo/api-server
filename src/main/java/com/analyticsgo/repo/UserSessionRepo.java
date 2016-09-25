package com.analyticsgo.repo;

import com.analyticsgo.model.User;
import com.analyticsgo.model.UserSession;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserSessionRepo extends CrudRepository<UserSession, String> {

  List<UserSession> findByUser(User user);

}
