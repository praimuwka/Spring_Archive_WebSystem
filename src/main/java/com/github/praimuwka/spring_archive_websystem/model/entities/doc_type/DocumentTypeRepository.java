package com.github.praimuwka.spring_archive_websystem.model.entities.doc_type;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentTypeRepository extends CrudRepository<DocumentType, Long> {
}
