package com.github.praimuwka.spring_archive_websystem.model.entities.document;

import com.github.praimuwka.spring_archive_websystem.model.entities.doc_type.DocumentType;
import com.github.praimuwka.spring_archive_websystem.model.entities.organisation.Organisation;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_document")
    private Long id;
    @NotNull(message = "поле не должно быть пустым")
    private String name;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type")
    @NotNull(message = "поле не должно быть пустым")
    private DocumentType type;
    private String author;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organisation")
    @NotNull(message = "поле не должно быть пустым")
    private Organisation organisation;
    private Integer yearCreated;
    @NotNull(message = "поле не должно быть пустым")
    private Integer pagesNumber;

    public void go() {
//        this.
    }

}
