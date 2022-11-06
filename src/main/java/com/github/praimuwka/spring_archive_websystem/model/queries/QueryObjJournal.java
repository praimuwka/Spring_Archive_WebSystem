package com.github.praimuwka.spring_archive_websystem.model.queries;

import lombok.Data;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
public class QueryObjJournal {
    private LocalDateTime datetime;
    private String action;
    private String document;
    private String employee;
    private String department;
    private Integer docId;

    public QueryObjJournal(Object[] record) {
        this.datetime = ((Timestamp) record[0]).toLocalDateTime();
        this.action = (String) record[1];
        this.document = (String) record[2];
        this.employee = (String) record[3];
        this.department = (String) record[4];
        this.docId = (Integer) record[5];
    }

}
