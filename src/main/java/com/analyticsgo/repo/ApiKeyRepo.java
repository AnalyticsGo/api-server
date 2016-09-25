package com.analyticsgo.repo;

import com.analyticsgo.model.ApiKey;
import com.analyticsgo.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ApiKeyRepo extends CrudRepository<ApiKey, String> {

  List<ApiKey> findByUser(User user);

}
