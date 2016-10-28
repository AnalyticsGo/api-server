package com.analyticsgo.repo;

import com.analyticsgo.model.DataTable;
import com.analyticsgo.model.User;
import com.analyticsgo.model.json.TableColumn;
import com.analyticsgo.test.RepoTest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class DataTableRepoTest extends RepoTest {

  private static final String TABLE_NAME = "TABLE_NAME";

  @Autowired
  private DataTableRepo dataTableRepo;

  @Autowired
  private UserRepo userRepo;

  @Before
  public void setUp() {
    User user = UserRepoTest.createUser();
    userRepo.save(user);
    DataTable table = createDataTable(user);
    dataTableRepo.save(table);
  }

  private DataTable createDataTable(User owner) {
    DataTable table = new DataTable();
    table.setName(TABLE_NAME);
    table.setOwner(owner);
    TableColumn dateColumn = new TableColumn();
    dateColumn.setId("month");
    dateColumn.setType("date");
    TableColumn salesColumn = new TableColumn();
    salesColumn.setId("sales");
    salesColumn.setType("number");
    table.setColumns(Arrays.asList(dateColumn, salesColumn));
    return table;
  }

  @Test
  public void testNameAndOwnerShouldBeUnique() {
    User user = userRepo.findByUsername(UserRepoTest.USERNAME);
    thrown.expect(DataIntegrityViolationException.class);
    DataTable table = createDataTable(user);
    dataTableRepo.save(table);
  }

  @Test
  public void testDataTableCreate() {
    List<DataTable> tables = new ArrayList<>();
    dataTableRepo.findAll().forEach(table -> tables.add(table));
    assertThat(tables, hasSize(1));
    DataTable table = tables.get(0);
    assertThat(table.getName(), is(TABLE_NAME));
    assertThat(table.getOwner(), is(userRepo.findByUsername(UserRepoTest.USERNAME)));
    List<TableColumn> columns = table.getColumns();
    assertThat(columns, hasSize(2));
    assertThat(columns.get(0).getId(), is("month"));
    assertThat(columns.get(0).getType(), is("date"));
    assertThat(columns.get(1).getId(), is("sales"));
    assertThat(columns.get(1).getType(), is("number"));
  }

  @Test
  public void testDataTableDelete() {
    DataTable table = dataTableRepo.findAll().iterator().next();
    dataTableRepo.delete(table);
    assertThat(dataTableRepo.findAll().iterator().hasNext(), is(false));
    User user = userRepo.findByUsername(UserRepoTest.USERNAME);
    assertThat(user, notNullValue());
  }

}
