package com.analyticsgo.model;

import com.analyticsgo.model.json.JsonConverter;
import com.analyticsgo.model.json.TableColumn;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@ToString(of = "name")
public class DataTable extends BaseEntity {

  @NotNull
  @ManyToOne
  @Setter
  @Getter
  private User owner;

  @NotNull
  @Setter
  @Getter
  private String name;

  @NotNull
  @Column(columnDefinition = "text")
  @Convert(converter = TableColumnsConverter.class)
  @Setter
  @Getter
  private List<TableColumn> columns;

  @PrePersist
  @PreUpdate
  public void checkColumns() {
    Set<String> columnIds = new HashSet<>();
    for (TableColumn column : columns) {
      if (columnIds.contains(column.getId())) {
        throw new IllegalStateException("Column id should be unique");
      }
      columnIds.add(column.getId());
    }
  }

  public static class TableColumnsConverter extends JsonConverter<List<TableColumn>> {

    @Override
    protected TypeReference<List<TableColumn>> createTypeRef() {
      return new TypeReference<List<TableColumn>>() {};
    }

  }

}
