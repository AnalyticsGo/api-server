package com.analyticsgo.repo;

import com.analyticsgo.model.DataTable;
import org.springframework.data.repository.CrudRepository;

public interface DataTableRepo extends CrudRepository<DataTable, Long> {
}
