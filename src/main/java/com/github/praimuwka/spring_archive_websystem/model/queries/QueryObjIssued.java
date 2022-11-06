package com.github.praimuwka.spring_archive_websystem.model.queries;

import lombok.Data;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
public class QueryObjIssued {
    private LocalDateTime datetime;
    private String document;
    private String employee;
    private String department;
    private Integer docId;

    public QueryObjIssued(Object[] record) {
        this.datetime = ((Timestamp) record[0]).toLocalDateTime();
        this.document = (String) record[1];
        this.employee = (String) record[2];
        this.department = (String) record[3];
        this.docId = (Integer) record[4];
    }
}
