package com.github.praimuwka.spring_archive_websystem.model.entities.journal;

import com.github.praimuwka.spring_archive_websystem.model.entities.department.Department;
import com.github.praimuwka.spring_archive_websystem.model.entities.document.Document;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface JournalRepository extends PagingAndSortingRepository<Journal, Long> {

    Optional<Journal> findByDocumentAndAndDatetimeReturnedAndDepartmentAndEmployee(
            Document document, LocalDateTime localDateTime, Department department, String employee);

    @Query(value = "CALL GET_JOURNAL();", nativeQuery = true)
    Iterable<Object[]> getMyJournal();

    @Query(value = "CALL GET_ISSUED();", nativeQuery = true)
    Iterable<Object[]> getMyIssued();

    @Query(value = "CALL GET_JOURNAL_PAGED(:pagesize, :pagenum);", nativeQuery = true)
    Iterable<Object[]> getMyJournalPaged(@Param("pagesize") Integer pagesize, @Param("pagenum") Integer pagenum);

    @Query(value = "CALL GET_ISSUED_PAGED(:pagesize, :pagenum);", nativeQuery = true)
    Iterable<Object[]> getMyIssuedPaged(@Param("pagesize") Integer pagesize, @Param("pagenum") Integer pagenum);
}
