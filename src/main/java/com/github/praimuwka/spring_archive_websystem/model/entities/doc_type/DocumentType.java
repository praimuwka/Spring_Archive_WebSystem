package com.github.praimuwka.spring_archive_websystem.model.entities.doc_type;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Data
@Entity
@Table(name = "document_type")
public class DocumentType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_doctype")
    private Long id;

    @Size(min = 3, message = "слишком короткое название ")
    private String name;
}
