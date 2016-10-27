package com.analyticsgo.service;

import com.analyticsgo.model.DataTable;
import com.analyticsgo.model.User;
import com.analyticsgo.model.json.TableColumn;
import com.analyticsgo.repo.DataTableRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DataTableService {

  private final DataTableRepo dataTableRepo;

  public DataTableService(DataTableRepo dataTableRepo) {
    this.dataTableRepo = dataTableRepo;
  }

  @Transactional
  public DataTable create(User owner, String name, List<TableColumn> columns) {
    DataTable table = new DataTable();
    table.setOwner(owner);
    table.setName(name);
    table.setColumns(columns);
    return dataTableRepo.save(table);
  }

}
