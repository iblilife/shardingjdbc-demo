package com.example.shardingjdbc.demo.model.popcorn.po;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "other_table")
public class OtherTable {
    @Id
    private Integer id;

    @Column(name = "test_column")
    private Integer test_column;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTest_column() {
        return test_column;
    }

    public void setTest_column(Integer test_column) {
        this.test_column = test_column;
    }
}
