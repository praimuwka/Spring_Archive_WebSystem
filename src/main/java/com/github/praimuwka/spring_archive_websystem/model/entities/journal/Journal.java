package com.github.praimuwka.spring_archive_websystem.model.entities.journal;

import com.github.praimuwka.spring_archive_websystem.model.entities.department.Department;
import com.github.praimuwka.spring_archive_websystem.model.entities.document.Document;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "issuance_registry")
@AllArgsConstructor
@NoArgsConstructor
public class Journal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_record")
    private Long id;
    @NotNull(message = "поле не должно быть пустым")
    private String employee;
    @NotNull(message = "поле не должно быть пустым")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "document")
    private Document document;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department")
    private Department department;
    private LocalDateTime datetimeGiven;
    private LocalDateTime datetimeReturned;
}
