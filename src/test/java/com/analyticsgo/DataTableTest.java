package com.analyticsgo;

import com.analyticsgo.model.DataTable;
import com.analyticsgo.model.User;
import com.analyticsgo.model.json.TableColumn;
import com.analyticsgo.repo.DataTableRepo;
import com.analyticsgo.service.DataTableService;
import com.analyticsgo.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DataTableTest {

  @Autowired
  private DataTableService dataTableService;

  @Autowired
  private UserService userService;

  @Test
  public void testCreateDataTableWithColumns() {
    User user = userService.findByUsername("userdemo");
    TableColumn dateColumn = new TableColumn();
    dateColumn.setId("date");
    dateColumn.setType("date");
    TableColumn salesColumn = new TableColumn();
    salesColumn.setId("sales");
    salesColumn.setType("number");
    dataTableService.create(user, "first_table", Arrays.asList(dateColumn, salesColumn));
  }

}
