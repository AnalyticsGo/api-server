package com.analyticsgo.repo;

import com.analyticsgo.model.ReadToken;
import com.analyticsgo.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ReadTokenRepo extends CrudRepository<ReadToken, String> {

  List<ReadToken> findByUser(User user);

}
